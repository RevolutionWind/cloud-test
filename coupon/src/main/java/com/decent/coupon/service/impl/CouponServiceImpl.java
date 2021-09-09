package com.decent.coupon.service.impl;

import com.decent.common.coupon.entities.CouponInfo;
import com.decent.common.coupon.entities.UserCoupon;
import com.decent.common.coupon.enums.CouponTypeEnum;
import com.decent.common.dto.CouponInfoDTO;
import com.decent.common.enums.ErrorCodeEnum;
import com.decent.common.exceptions.ErrorCodeException;
import com.decent.common.pojo.SimpleMessage;
import com.decent.coupon.dao.CouponDao;
import com.decent.coupon.service.CouponService;
import com.decent.coupon.util.RedisTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 用户优惠券管理
 *
 * @author sunxy
 * @date 2020/12/31
 */
@Slf4j
@Service
public class CouponServiceImpl implements CouponService {

    @Resource
    private CouponDao couponDao;
    @Resource
    private RedisTool redisTool;

    /**
     * 领取优惠券
     *
     * @return SimpleMessage
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleMessage obtainCoupon(String userCode, Long couponId) {
        //Redis KEY
        String lockKey = UUID.randomUUID().toString();
        //用户唯一标识
        Long requestId = System.currentTimeMillis() + 20000L;
        boolean lock = false;
        try {
            //加锁
            lock = redisTool.lock(lockKey, String.valueOf(requestId));
            if (!lock) {
                throw new ErrorCodeException(ErrorCodeEnum.NO, "领取人数较多，请尝试再次点击领取！");
            }
            //领取优惠券
            LocalDate endTime = LocalDate.now().plusDays(1);
            LocalDate startTime = endTime.minusDays(30);
            //先查询该用户在一个月之内是否领取过此优惠券
            List<UserCoupon> userCoupons = couponDao.listUserMonthCoupon(userCode, startTime, endTime, couponId);
            if (!userCoupons.isEmpty()) {
                throw new ErrorCodeException(ErrorCodeEnum.NO, "已达到最大领取数量！");
            }
            //查询优惠券库存
            CouponInfo couponInfo = couponDao.listCouponById(couponId);
            if (Objects.isNull(couponInfo)) {
                throw new ErrorCodeException(ErrorCodeEnum.NO, "不好意思，优惠券已领完！");
            }
            //修改库存
            int i = couponDao.updateCoupon(couponId);
            if (i != 1) {
                throw new ErrorCodeException(ErrorCodeEnum.NO, "库存不足！");
            }
            //领取优惠券
            int num = couponDao.obtainCoupon(userCode, couponId);
            if (num != 1) {
                throw new ErrorCodeException(ErrorCodeEnum.NO, "领取优惠券失败！");
            }
        } catch (ErrorCodeException e) {
            log.error("[{}]领取优惠券失败", userCode, e);
        } finally {
            //解锁
            if (lock) {
                redisTool.unlock(lockKey, String.valueOf(requestId));
            }
        }
        return new SimpleMessage(ErrorCodeEnum.OK, "优惠券领取成功！");
    }

    /**
     * 查询所有可以领取的优惠券
     *
     * @param couponTypeEnum 优惠券类型
     * @return List<CouponInfoDTO>
     */
    @Override
    public List<CouponInfoDTO> listCoupon(CouponTypeEnum couponTypeEnum) {
        return couponDao.listCoupon(couponTypeEnum);
    }

    /**
     * 查询用户已领取的优惠券
     *
     * @param userCode 用户唯一标识
     * @return List<CouponInfoDTO>
     */
    @Override
    public List<CouponInfoDTO> listUserNotCoupon(String userCode) {
        return couponDao.listUserNotCoupon(userCode);
    }

    /**
     * 查询用户已领取已使用或者已过期的优惠券
     *
     * @param userCode 用户唯一标识
     * @return List<CouponInfoDTO>
     */
    @Override
    public List<CouponInfoDTO> listUserCoupon(String userCode) {
        List<CouponInfoDTO> list = new ArrayList<>();
        LocalDate endTime = LocalDate.now().plusDays(1);
        LocalDate startTime = endTime.minusDays(30);
        List<CouponInfoDTO> listUserCoupon = couponDao.listUserCoupon(userCode, startTime, endTime);
        List<CouponInfoDTO> listUserIsCoupon = couponDao.listUserIsCoupon(userCode, startTime, endTime);
        list.addAll(listUserCoupon);
        list.addAll(listUserIsCoupon);
        return list;
    }

    /**
     * 核销优惠券
     *
     * @param userCouponId 用户优惠券id
     * @param userCode     用户code
     * @return SimpleMessage
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SimpleMessage consumeUserCoupon(Long userCouponId, String userCode) {
        if (couponDao.consumeUserCoupon(userCouponId, userCode) > 0) {
            return new SimpleMessage(ErrorCodeEnum.OK, "核销成功");
        }
        throw new ErrorCodeException(ErrorCodeEnum.NO, "优惠券核销失败");
    }
}
