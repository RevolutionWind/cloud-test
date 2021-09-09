package com.decent.common.dto;

import com.decent.common.coupon.entities.CouponInfo;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 优惠券的DTO
 *
 * @author sunxy
 * @date 2021/1/3
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponInfoDTO extends CouponInfo {

    private static final long serialVersionUID = 7781659741090022131L;
    /**
     * 用户优惠券关联ID
     */
    private Long userCouponId;

    /**
     * 使用时间
     */
    private LocalDateTime usedTime;

}
