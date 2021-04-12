package com.decent.common.coupon.entities;

import com.decent.common.coupon.enums.UseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户、优惠券绑定
 *
 * @author zhangjun
 * @date 2020/12/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserCoupon {

    /**
     * 绑定主键
     */
    private Long id;

    /**
     * 用户唯一标识
     */
    private String userCode;

    /**
     * 优惠券主键
     */
    private Long couponId;

    /**
     * 使用状态
     */
    private UseStatusEnum useStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

}
