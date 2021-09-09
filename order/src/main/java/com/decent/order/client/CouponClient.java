package com.decent.order.client;

import com.decent.common.dto.CouponInfoDTO;
import com.decent.common.pojo.SimpleMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 优惠券对外服务
 *
 * @author sunxy
 * @date 2020/1/3
 */
@Component
@FeignClient(value = "coupon-service")
@RequestMapping("/coupon")
public interface CouponClient {

    /**
     * 查询用户已领取的优惠券未使用
     *
     * @param userCode 用户唯一标识
     * @return List<CouponInfo>
     */
    @GetMapping("/list")
    List<CouponInfoDTO> listUserNotCoupon(@RequestParam("userCode") String userCode);

    /**
     * 查询用户已领取已使用或者已过期的优惠券
     *
     * @param userCode 用户唯一标识
     * @return List<CouponInfoDTO>
     */
    @PostMapping("/user/coupon")
    List<CouponInfoDTO> listUserCoupon(@RequestParam("userCode") String userCode);

    /**
     * 核销优惠券
     *
     * @param userCouponId 用户优惠券id
     * @param userCode     用户code
     * @return SimpleMessage
     */
    @PostMapping("/consumeUserCoupon")
    SimpleMessage consumeUserCoupon(@RequestParam("userCouponId") Long userCouponId, @RequestParam("userCode") String userCode);

}
