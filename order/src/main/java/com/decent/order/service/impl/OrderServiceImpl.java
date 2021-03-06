package com.decent.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.decent.common.constant.integral.QueueKeyConstant;
import com.decent.common.coupon.entities.CouponInfo;
import com.decent.common.dto.CouponInfoDTO;
import com.decent.common.enity.ProductOrder;
import com.decent.common.enity.UserOrderProduct;
import com.decent.common.enity.product.product.ProductInfo;
import com.decent.common.enums.ErrorCodeEnum;
import com.decent.common.exceptions.ErrorCodeException;
import com.decent.common.mq.entity.TransactionsPriceCountDTO;
import com.decent.common.pojo.CommonConstant;
import com.decent.common.pojo.GoodsStock;
import com.decent.common.pojo.MessageBean;
import com.decent.common.pojo.Response;
import com.decent.common.util.HttpClient;
import com.decent.common.util.Md5;
import com.decent.common.util.OrderNoUtil;
import com.decent.order.client.CouponClient;
import com.decent.order.client.ProductClient;
import com.decent.order.dao.OrderDao;
import com.decent.order.service.OrderService;
import com.decent.order.utils.MqSendProducer;
import com.decent.order.vo.ProductOrderVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author sunxy
 * @date 2021/1/2
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private MqSendProducer mqSendProducer;
    @Resource
    private CouponClient couponClient;
    @Resource
    private ProductClient productClient;

    @Override
    public PageInfo<ProductOrderVO> listOrder(String userCode, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum() <= 0 ? 1 : pageInfo.getPageNum(), pageInfo.getPageSize() <= 0 ? 10 : pageInfo.getPageSize());
        return new PageInfo<>(orderDao.listOrder(userCode));
    }

    @Override
    @GlobalTransactional(name = "order_now", rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public MessageBean orderNow(Long goodsId, Long cardCount, String userCode, String agentId, Boolean isErr) {
        // ????????????????????????
        // checkUser()~~~~~~~~~~;
        // ????????????????????? ???????????????????????????
        // checkAgent()~~~~~~~~~;
        // ?????????????????? ????????????
        ProductInfo productInfo = productClient.getProductDetail(String.valueOf(goodsId));
        if (Objects.isNull(productInfo)) {
            return new MessageBean(ErrorCodeEnum.NO.getCode(), "???????????????");
        }
        // ?????????????????? ????????????????????????
//        List<GoodsStock> goodsStock = getGoodsStock(userCode, String.valueOf(goodsId));
//        log.info("[{}]", goodsStock);
//        if (CollectionUtils.isEmpty(goodsStock)) {
//            return new MessageBean(ErrorCodeEnum.NO.getCode(), "?????????????????????");
//        }
//        GoodsStock requestGood = goodsStock.get(0);
//        if (requestGood.getStockNum() < cardCount) {
//            return new MessageBean(ErrorCodeEnum.NO.getCode(), "??????????????????");
//        }
        // ????????????????????????????????????????????????????????????
        List<CouponInfoDTO> noUseCoupon = couponClient.listUserNotCoupon(userCode);
        Optional<CouponInfoDTO> maxCoupon = noUseCoupon.stream().max(Comparator.comparing(CouponInfo::getReductionPrice));
        maxCoupon.ifPresent(coupon -> couponClient.consumeUserCoupon(coupon.getUserCouponId(), userCode));
        // ??????????????????
        buildOrder(goodsId, cardCount, userCode, productInfo, maxCoupon);
        if (Boolean.TRUE.equals(isErr)) {
            throw new ErrorCodeException(ErrorCodeEnum.NO, "?????????");
        }
        // ?????????????????????,?????????rabbitMq??????
        mqSendProducer.sendMessage(QueueKeyConstant.USER_TRANSACTIONS_COUNT_KEY,
                JSONObject.toJSONString(TransactionsPriceCountDTO.builder()
                        .userCode(userCode).agentId(agentId).transactionsPrice(BigDecimal.TEN).build()));
        // ???????????????
        mqSendProducer.sendMessage(QueueKeyConstant.USER_INTEGRAL_ROUTING_KEY,
                JSONObject.toJSONString(TransactionsPriceCountDTO.builder()
                        .userCode(userCode).agentId(agentId).transactionsPrice(BigDecimal.TEN).build()));
        // ??????????????????
        mqSendProducer.sendMessage(QueueKeyConstant.AGENT_INTEGRAL_ROUTING_KEY,
                JSONObject.toJSONString(TransactionsPriceCountDTO.builder()
                        .userCode(userCode).agentId(agentId).transactionsPrice(BigDecimal.TEN).build()));
        return new MessageBean(ErrorCodeEnum.OK);
    }

    /**
     * ????????????
     *
     * @param goodsId     ??????id
     * @param cardCount   ????????????
     * @param userCode    ????????????
     * @param productInfo ????????????
     */
    private void buildOrder(Long goodsId, Long cardCount, String userCode, ProductInfo productInfo,
                            Optional<CouponInfoDTO> maxPriceCoupon) {
        String orderNo = OrderNoUtil.generateOrderNo(OrderNoUtil.CARD_ORDER_NO_PREFIX);
        ProductOrder productOrder = ProductOrder.builder().userCode(userCode).orderNo(orderNo).productId(goodsId)
                .totalPrice(productInfo.getSellPrice().multiply(new BigDecimal(cardCount)))
                .productId(productInfo.getId()).productName(productInfo.getProductName()).iconUrl(productInfo.getPicUrl())
                .count(cardCount).payPrice(productInfo.getSellPrice().multiply(new BigDecimal(cardCount))).orderStatus("WAIT_PAY").build();
        maxPriceCoupon.ifPresent(o -> {
            productOrder.setUserCouponId(o.getId());
            productOrder.setCouponPrice(o.getReductionPrice());
            // ?????????????????? = ????????? - ?????????????????????
            productOrder.setPayPrice(productOrder.getPayPrice().subtract(o.getReductionPrice()));
        });
        log.info("???????????? = [{}], traceId = [{}]", orderNo, TraceContext.traceId());
        ActiveSpan.info("????????????" + orderNo + ", ???????????????");
        orderDao.insertOrder(productOrder);
    }

    @Override
    public MessageBean queryOrderCoupon(String userCode, String orderNo) {
        UserOrderProduct userOrderProduct = orderDao.queryOrderCoupon(userCode, orderNo);
        return new MessageBean(userOrderProduct);
    }

    /**
     * ????????????????????????
     *
     * @param userCode ?????????
     * @param goodsId  ??????id
     * @return List<GoodsStock>
     */
    @SuppressWarnings("unused")
    private List<GoodsStock> getGoodsStock(String userCode, String goodsId) {
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("app_id", "15265710813");
        treeMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        treeMap.put("goods_no", goodsId);
        List<NameValuePair> params = getNameValuePairs(treeMap);
        log.info("?????????[{}]?????????????????????[{}]", userCode, params);
        String result;
        try {
            result = HttpClient.post("http://192.168.60.177:4321/coupon/goods/stock", params, "UTF-8", 15000, 15000);
            log.info("?????????[{}]???????????????????????????[{}]", userCode, result);
            Response<String> response = JSON.parseObject(result, new TypeReference<Response<String>>() {
            });
            if (CommonConstant.SUCCESS_CODE.equals(response.getCode())) {
                return JSONArray.parseArray(response.getData(), GoodsStock.class);
            } else {
                log.warn("?????????[{}]???????????????????????????????????????[{}]:[{}]", userCode, response.getCode(), response.getMessage());
                return Collections.emptyList();
            }
        } catch (Exception e) {
            log.warn("?????????[{}]????????????????????????????????????[{}]", userCode, e);
            return Collections.emptyList();
        }
    }

    /**
     * ?????????????????????sign
     *
     * @param treeMap ????????????
     * @return ????????????
     */
    private List<NameValuePair> getNameValuePairs(TreeMap<String, String> treeMap) {
        Iterator<Map.Entry<String, String>> iterator = treeMap.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (StringUtils.isNotBlank(entry.getValue())) {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("&");
            }
        }
        sb.append("key=").append("9017DD08871D84B8B3A96520890DFC24");
        String sign = Md5.getMd5(sb.toString(), true, "UTF-8");
        treeMap.put("sign", sign);
        List<NameValuePair> params = new ArrayList<>();
        treeMap.forEach((k, v) -> params.add(new BasicNameValuePair(k, v)));
        return params;
    }
}
