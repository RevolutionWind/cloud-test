package com.decent.coupon.controller;

import com.decent.common.coupon.entities.Page;
import com.decent.common.coupon.enums.CouponTypeEnum;
import com.decent.common.dto.CouponInfoDTO;
import com.decent.common.enums.ErrorCodeEnum;
import com.decent.common.exceptions.ErrorCodeException;
import com.decent.common.pojo.SimpleMessage;
import com.decent.coupon.service.CouponService;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 用户优惠券
 *
 * @author sunxy
 * @date 2020/12/31
 */
@RestController
@RequestMapping("coupon")
public class CouponController {

    @Resource
    private CouponService couponService;

    /**
     * 查询用户已领取的优惠券未使用
     *
     * @param userCode 用户唯一标识
     * @return List<CouponInfo>
     */
    @RequestMapping("list")
    public List<CouponInfoDTO> listUserNotCoupon(String userCode) {
        return couponService.listUserNotCoupon(userCode);
    }

    /**
     * 查询用户已领取已使用或者已过期的优惠券
     *
     * @param userCode 用户唯一标识
     * @return List<CouponInfoDTO>
     */
    @RequestMapping("user/coupon")
    public List<CouponInfoDTO> listUserCoupon(String userCode) {
        return couponService.listUserCoupon(userCode);
    }

    /**
     * 查询用户可以领取的优惠券
     *
     * @param pageNumber     条数
     * @param pageSize       页数
     * @param couponTypeEnum 优惠券类型
     * @return Page<CouponInfo>
     */
    @RequestMapping("listCanReceive")
    public Page<CouponInfoDTO> listCoupon(int pageNumber, int pageSize, CouponTypeEnum couponTypeEnum) {
        if (Objects.isNull(couponTypeEnum)) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS, "请输入优惠券类型");
        }
        PageHelper.startPage(pageNumber, pageSize);
        return new Page<>(couponService.listCoupon(couponTypeEnum));
    }

    /**
     * 领取优惠券
     *
     * @param userCode 用户唯一标识
     * @param couponId 绑定ID
     * @return SimpleMessage
     */
    @RequestMapping("/get/{couponId}")
    public SimpleMessage obtainCoupon(@RequestParam("couponId") Long couponId, @RequestParam("userCode") String userCode) {
        return couponService.obtainCoupon(userCode, couponId);
    }

    /**
     * 核销优惠券
     *
     * @param userCouponId 用户优惠券id
     * @param userCode     用户code
     * @return SimpleMessage
     */
    @PostMapping("/consumeUserCoupon")
    public SimpleMessage consumeUserCoupon(Long userCouponId, String userCode) {
        return couponService.consumeUserCoupon(userCouponId, userCode);
    }

}
