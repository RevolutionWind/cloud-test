package com.decent.common.coupon.entities;

import com.decent.common.coupon.enums.CouponTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券信息
 *
 * @author zhangjun
 * @date 2020/12/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponInfo implements Serializable {

    private static final long serialVersionUID = 7491190394770880275L;
    /**
     * 优惠券主键
     */
    private Long id;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 优惠券类型
     */
    private CouponTypeEnum couponType;

    /**
     * 满足金额
     */
    private BigDecimal reductionPrice;

    /**
     * 面值
     */
    private BigDecimal face;

    /**
     * 优惠券详情
     */
    private String description;

    /**
     * 有效期开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime effectiveTimeStart;

    /**
     * 有效期结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime effectiveTimeEnd;

    /**
     * 优惠券库存
     */
    private Long couponNumber;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
