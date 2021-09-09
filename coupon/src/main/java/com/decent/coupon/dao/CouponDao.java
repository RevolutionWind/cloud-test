package com.decent.coupon.dao;

import com.decent.common.coupon.entities.CouponInfo;
import com.decent.common.coupon.entities.UserCoupon;
import com.decent.common.coupon.enums.CouponTypeEnum;
import com.decent.common.dto.CouponInfoDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * 优惠券SQL
 *
 * @author sunxy
 * @date 2020/12/19
 */
@Mapper
@Repository
public interface CouponDao {

    /**
     * 查询用户可以领取的所有优惠券
     *
     * @param couponTypeEnum 优惠券类型
     * @return List<CouponInfoDTO>
     */
    @Select("SELECT " +
            "id, " +
            "coupon_name, " +
            "coupon_type, " +
            "reduction_price, " +
            "face, " +
            "description, " +
            "effective_time_start, " +
            "effective_time_end, " +
            "coupon_number,  " +
            "create_time,  " +
            "update_time  " +
            "FROM " +
            "coupon_info  " +
            "WHERE " +
            "coupon_number > 0  " +
            "AND effective_time_end > NOW()  " +
            "AND coupon_type = #{couponTypeEnum}")
    List<CouponInfoDTO> listCoupon(@Param("couponTypeEnum") CouponTypeEnum couponTypeEnum);

    /**
     * 查询用户已领取未使用未过期的优惠券
     *
     * @param userCode 用户唯一标识
     * @return List<CouponInfoDTO>
     */
    @Select("SELECT " +
            "i.id, " +
            "i.coupon_name, " +
            "i.coupon_type, " +
            "i.reduction_price, " +
            "i.face, " +
            "i.description, " +
            "i.effective_time_start, " +
            "i.effective_time_end, " +
            "c.id AS userCouponId, " +
            "c.used_time " +
            "FROM " +
            "user_coupon AS c " +
            "INNER JOIN coupon_info AS i ON i.id = c.coupon_id  " +
            "WHERE " +
            "i.effective_time_end > NOW()  " +
            "AND c.use_status = 'NOT_USED'  " +
            "AND c.user_code = #{userCode} " +
            "ORDER BY i.effective_time_end DESC")
    List<CouponInfoDTO> listUserNotCoupon(@Param("userCode") String userCode);

    /**
     * 查询用户已领取未使用已过期的优惠券
     *
     * @param userCode  用户唯一标识
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return List<CouponInfoDTO>
     */
    @Select("SELECT " +
            "i.id, " +
            "i.coupon_name, " +
            "i.coupon_type, " +
            "i.reduction_price, " +
            "i.face, " +
            "i.description, " +
            "i.effective_time_start, " +
            "i.effective_time_end, " +
            "c.id AS userCouponId, " +
            "c.used_time " +
            "FROM " +
            "user_coupon AS c " +
            "INNER JOIN coupon_info AS i ON i.id = c.coupon_id  " +
            "WHERE " +
            "i.effective_time_end <= NOW()  " +
            "AND c.use_status = 'NOT_USED'  " +
            "AND c.user_code = #{userCode} " +
            "AND i.effective_time_end >= #{startTime} " +
            "AND i.effective_time_end <= #{endTime} " +
            "ORDER BY i.effective_time_end DESC")
    List<CouponInfoDTO> listUserCoupon(@Param("userCode") String userCode, @Param("startTime") LocalDate startTime,
                                       @Param("endTime") LocalDate endTime);

    /**
     * 查询用户已领取已使用的优惠券
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param userCode  用户唯一标识
     * @return List<CouponInfoDTO>
     */
    @Select("SELECT " +
            "i.id, " +
            "i.coupon_name, " +
            "i.coupon_type, " +
            "i.reduction_price, " +
            "i.face, " +
            "i.description, " +
            "i.effective_time_start, " +
            "i.effective_time_end, " +
            "c.id AS userCouponId, " +
            "c.used_time " +
            "FROM " +
            "user_coupon AS c " +
            "INNER JOIN coupon_info AS i ON i.id = c.coupon_id  " +
            "WHERE " +
            "c.use_status = 'USED' " +
            "AND c.user_code = #{userCode} " +
            "AND c.used_time >= #{startTime} " +
            "AND c.used_time <= #{endTime} " +
            "ORDER BY c.used_time DESC")
    List<CouponInfoDTO> listUserIsCoupon(@Param("userCode") String userCode, @Param("startTime") LocalDate startTime,
                                         @Param("endTime") LocalDate endTime);

    /**
     * 领取优惠券
     *
     * @param userCode 用户唯一标识
     * @param couponId 优惠券ID
     * @return int
     */
    @Insert("INSERT INTO user_coupon ( user_code, coupon_id, use_status, create_time ) " +
            "VALUES " +
            "(#{userCode}, " +
            "#{couponId},'NOT_USED',NOW())")
    int obtainCoupon(@Param("userCode") String userCode, @Param("couponId") Long couponId);

    /**
     * 查询用户可以领取的所有优惠券
     *
     * @param id 优惠券ID
     * @return CouponInfo
     */
    @Select("SELECT " +
            "id, " +
            "coupon_name, " +
            "coupon_type, " +
            "reduction_price, " +
            "face, " +
            "description, " +
            "effective_time_start, " +
            "effective_time_end, " +
            "coupon_number  " +
            "FROM " +
            "coupon_info  " +
            "WHERE " +
            "coupon_number >= 1  " +
            "AND effective_time_end > NOW()  " +
            "AND id = #{id} for update")
    @Options(timeout = 3)
    CouponInfo listCouponById(@Param("id") Long id);


    /**
     * 修改库存
     *
     * @param id 优惠券ID
     * @return int
     */
    @Update("UPDATE coupon_info SET coupon_number = coupon_number - 1 WHERE id = #{id}")
    int updateCoupon(@Param("id") Long id);

    /**
     * 查询该用户一个月之内是否已领取此优惠券
     *
     * @param userCode  用户唯一标识
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param couponId  优惠券ID
     * @return List<UserCoupon>
     */
    @Select("SELECT * FROM user_coupon WHERE user_code = #{userCode} AND coupon_id = #{couponId} " +
            "AND create_time >= #{startTime} AND create_time <= #{endTime}")
    List<UserCoupon> listUserMonthCoupon(@Param("userCode") String userCode, @Param("startTime") LocalDate startTime,
                                         @Param("endTime") LocalDate endTime, @Param("couponId") Long couponId);

    /**
     * 核销优惠券
     *
     * @param userCouponId 用户优惠券id
     * @param userCode     用户码
     * @return 影响的行数
     */
    @Update("UPDATE user_coupon " +
            "SET use_status  = 'USED', " +
            "    used_time   = NOW(), " +
            "    update_time = NOW() " +
            "WHERE id = #{userCouponId} " +
            "  AND user_code = #{userCode} " +
            "  AND use_status = 'NOT_USED'")
    int consumeUserCoupon(@Param("userCouponId") Long userCouponId, @Param("userCode") String userCode);
}
