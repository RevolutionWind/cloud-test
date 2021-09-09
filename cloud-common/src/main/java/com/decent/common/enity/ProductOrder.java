package com.decent.common.enity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author sunxy
 * @date 2021/1/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrder implements Serializable {
    private static final long serialVersionUID = -2305316408907122916L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户唯一标识
     */
    private String userCode;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商品总金额
     */
    private BigDecimal totalPrice;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 品牌图标url
     */
    private String iconUrl;

    /**
     * 购买数量
     */
    private Long count;

    /**
     * 实付金额
     */
    private BigDecimal payPrice;

    /**
     * 会员优惠券id
     */
    private Long userCouponId;

    /**
     * 优惠券减免金额
     */
    private BigDecimal couponPrice;

    /**
     * 返还的积分数
     */
    private Long integral;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 支付成功时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime successTime;
    /**
     * 使用截至时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endValidTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
