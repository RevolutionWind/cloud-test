package com.decent.user.client;

import com.decent.common.pojo.MessageBean;
import com.decent.common.pojo.SimpleMessage;
import com.decent.common.vo.ProductOrderVO;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author sunxy
 * @date 2021-01-02
 */
@FeignClient(value = "order-service")
public interface OrderClient {

    /**
     * 查询订单
     *
     * @param userCode 会员编号
     * @param pageInfo 分页信息
     * @return success
     */
    @PostMapping("/cloudOrder/listOrder")
    PageInfo<ProductOrderVO> listOrder(@RequestParam("agentId") String userCode, @RequestParam("pageInfo") PageInfo<?> pageInfo);

    /**
     * 查询订单
     *
     * @param userCode 会员编号
     * @param orderNo  订单号
     * @return success
     */
    @PostMapping("/cloudOrder/queryOrderCoupon")
    SimpleMessage queryOrderCoupon(@RequestParam("agentId") String userCode, @RequestParam("orderNo") String orderNo);

    /**
     * 生成订单
     *
     * @param goodsId   商品编号
     * @param cardCount 购买数量
     * @param userCode  订单号
     * @return MessageBean
     */
    @PostMapping("/cloudOrder/orderNow")
    MessageBean queryOrderCoupon(@RequestParam("goodsId") Long goodsId, @RequestParam("cardCount") Long cardCount,
                                 @RequestParam("userCode") String userCode);

}