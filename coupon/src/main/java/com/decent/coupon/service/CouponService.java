package com.decent.coupon.service;

import com.decent.common.coupon.enums.CouponTypeEnum;
import com.decent.common.dto.CouponInfoDTO;
import com.decent.common.pojo.SimpleMessage;

import java.util.List;

/**
 * 优惠券管理
 *
 * @author zhangjun
 * @date 2020/12/31
 */
public interface CouponService {

    /**
     * 领取优惠券
     *
     * @param userCode 用户唯一标识
     * @param couponId 绑定ID
     * @return SimpleMessage
     */
    SimpleMessage obtainCoupon(String userCode, Long couponId);

    /**
     * 查询所有可以领取的优惠券
     *
     * @param couponTypeEnum 优惠券类型
     * @return List<CouponInfoDTO>
     */
    List<CouponInfoDTO> listCoupon(CouponTypeEnum couponTypeEnum);

    /**
     * 查询用户已领取的优惠券未使用
     *
     * @param userCode 用户唯一标识
     * @return List<CouponInfoDTO>
     */
    List<CouponInfoDTO> listUserNotCoupon(String userCode);

    /**
     * 查询用户已领取已使用或者已过期的优惠券
     *
     * @param userCode 用户唯一标识
     * @return List<CouponInfoDTO>
     */
    List<CouponInfoDTO> listUserCoupon(String userCode);

    /**
     * 核销优惠券
     *
     * @param userCouponId 用户优惠券id
     * @param userCode     用户code
     * @return SimpleMessage
     */
    SimpleMessage consumeUserCoupon(Long userCouponId, String userCode);

}
