package com.decent.user.service;


import com.decent.common.dto.CouponInfoDTO;
import com.decent.common.mq.entity.TransactionsPriceCountDTO;
import com.decent.common.pojo.MessageBean;
import com.decent.common.vo.ProductOrderVO;
import com.decent.common.vo.UserInfoVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 用户交易额
 *
 * @author sunxy
 * @date 2021/1/10 9:53
 */
public interface UserInfoService {

    /**
     * 修改用户的交易额
     *
     * @param dto TransactionsPriceCountDTO
     */
    void changeUserTransaction(TransactionsPriceCountDTO dto);

    /**
     * 我的订单
     *
     * @param userCode 用户唯一编码
     * @param pageInfo 分页实体
     * @return 订单
     */
    PageInfo<ProductOrderVO> listOrder(String userCode, PageInfo<?> pageInfo);

    /**
     * 获取优惠券
     *
     * @param userCode 用户唯一编码
     * @return 优惠券
     */
    List<CouponInfoDTO> listUserNotCoupon(String userCode);

    /**
     * 获取用户积分
     *
     * @param userCode 用户唯一编码
     * @return 积分
     */
    MessageBean getUserIntegral(String userCode);

    /**
     * 获取用户信息
     *
     * @param userCode 用户唯一编码
     * @return 用户信息
     */
    UserInfoVO profile(String userCode);
}
