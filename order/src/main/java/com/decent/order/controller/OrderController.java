package com.decent.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.decent.common.enums.ErrorCodeEnum;
import com.decent.common.exceptions.ErrorCodeException;
import com.decent.common.pojo.MessageBean;
import com.decent.order.service.OrderService;
import com.decent.order.vo.ProductOrderVO;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangy
 * @date 2021/1/2
 */
@RestController
@RequestMapping("order")
public class OrderController {
    @Resource
    private OrderService orderService;

    /**
     * 查询订单
     *
     * @param userCode 会员编号
     * @param pageInfo 分页信息
     * @return 订单列表
     */
    @RequestMapping("user/order")
    public PageInfo<ProductOrderVO> listOrder(String userCode, PageInfo<?> pageInfo) {
        if (StringUtils.isBlank(userCode)) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS);
        }
        return orderService.listOrder(userCode, pageInfo);
    }

    /**
     * 查询订单
     *
     * @param userCode 会员编号
     * @param pageInfo 分页信息
     * @return 订单列表
     */
    @RequestMapping("testListOrder")
    public PageInfo<ProductOrderVO> testListOrder(String userCode, PageInfo<?> pageInfo) {
        return orderService.listOrder(userCode, pageInfo);
    }

    /**
     * 查看卡券
     *
     * @param userCode 会员编号
     * @param orderId  订单编号
     * @return 卡券信息
     */
    @RequestMapping("product/details/{orderId}")
    public MessageBean queryOrderCoupon(@RequestParam("userCode") String userCode, @PathVariable String orderId) {
        if (StringUtils.isAnyBlank(userCode, orderId)) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS);
        }
        return orderService.queryOrderCoupon(userCode, orderId);
    }

    /**
     * 生成订单
     *
     * @param goodsId   橙券产品id
     * @param cardCount 购买数量
     * @param userCode  会员编号
     * @param isErr     是否抛出异常
     * @param isSleep   是否sleep
     * @return MessageBean
     */
    @SentinelResource(value = "pay-sentinel", fallback = "payFallback", blockHandler = "payBlock")
    @RequestMapping("pay")
    public MessageBean orderNow(Long goodsId, Long cardCount, String userCode,
                                String agentId, Boolean isErr, Boolean isSleep) throws InterruptedException {
        if (StringUtils.isAnyBlank(userCode, agentId)) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_RSA_KEY.getCode(), "获取用户信息失败");
        }
        if (Boolean.TRUE.equals(isSleep)) {
            TimeUnit.SECONDS.sleep(1);
        }
        // 校验购买数量
        if (cardCount == null || cardCount <= 0) {
            return new MessageBean(ErrorCodeEnum.NO.getCode(), "请选择购买数量");
        }
        // 校验产品
        if (Objects.isNull(goodsId)) {
            return new MessageBean(ErrorCodeEnum.NO.getCode(), "请选择购买商品");
        }
        // 生成订单
        return orderService.orderNow(goodsId, cardCount, userCode, agentId, isErr);
    }

    /**
     * 支付服务兜底异常处理（sentinel用）
     *
     * @param goodsId   橙券产品id
     * @param cardCount 购买数量
     * @param userCode  会员编号
     * @param agentId   代理商编号
     * @param isErr     是否抛出异常
     * @param e         异常
     * @return MessageBean
     */
    @SuppressWarnings({"unused", "com.decent.order.controller.OrderController.orderNow"})
    public MessageBean payFallback(Long goodsId, Long cardCount, String userCode, String agentId,
                                   Boolean isErr, Boolean isSleep, Throwable e) {
        if (e instanceof ErrorCodeException) {
            if (((ErrorCodeException) e).getCode().equals(ErrorCodeEnum.INVALID_RSA_KEY.getCode())) {
                return new MessageBean(ErrorCodeEnum.ERROR, "未通过授权，请重新登录");
            }
            if (((ErrorCodeException) e).getCode().equals(ErrorCodeEnum.NO.getCode())) {
                return new MessageBean(ErrorCodeEnum.ERROR, "异常熔断， message = " + e.getMessage());
            }
        }
        return new MessageBean(ErrorCodeEnum.ERROR, "服务繁忙，请稍后再试");
    }

    /**
     * 支付服务流控处理（sentinel用）
     *
     * @param goodsId   橙券产品id
     * @param cardCount 购买数量
     * @param userCode  会员编号
     * @param agentId   代理商编号
     * @param isErr     是否抛出异常
     * @param e         流控异常
     * @return MessageBean
     */
    @SuppressWarnings({"unused", "com.decent.order.controller.OrderController.orderNow"})
    public MessageBean payBlock(Long goodsId, Long cardCount, String userCode, String agentId,
                                Boolean isErr, Boolean isSleep, BlockException e) {
        if (e instanceof DegradeException) {
            return new MessageBean(ErrorCodeEnum.FREQUENTLY_REQUEST, "服务降级，请稍后再试");
        }
        if (e instanceof FlowException) {
            return new MessageBean(ErrorCodeEnum.FREQUENTLY_REQUEST, "服务流控，请稍后再试");
        }
        return new MessageBean(ErrorCodeEnum.FREQUENTLY_REQUEST, "服务不可用，请稍后再试");
    }
}
