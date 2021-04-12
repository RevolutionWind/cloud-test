package com.decent.order.service;

import com.decent.common.pojo.MessageBean;
import com.decent.order.vo.ProductOrderVO;
import com.github.pagehelper.PageInfo;

/**
 * @author jiangy
 * @date 2021/1/2
 */
public interface OrderService {

    /**
     * 订单查询
     *
     * @param userCode 用户编号
     * @param pageInfo 分页信息
     * @return 订单列表
     */
    PageInfo<ProductOrderVO> listOrder(String userCode, PageInfo<?> pageInfo);

    /**
     * 订单查询
     *
     * @param userCode 用户编号
     * @param orderNo  订单号
     * @return 订单列表
     */
    MessageBean queryOrderCoupon(String userCode, String orderNo);

    /**
     * 下单接口
     *
     * @param goodsId   商品id
     * @param cardCount 购买数量
     * @param userCode  会员编号
     * @param agentId   代理商编号
     * @param isErr     isErr
     * @return messageBean
     */
    MessageBean orderNow(Long goodsId, Long cardCount, String userCode, String agentId, Boolean isErr);
}
