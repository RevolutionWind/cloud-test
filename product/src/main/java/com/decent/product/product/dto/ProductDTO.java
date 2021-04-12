package com.decent.product.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Max
 * @date 2021/1/10 11:38
 */
@ToString(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    /**
     * 商品名
     */
    private String productName;

    /**
     * 分类id
     */
    private Long brandId;

    /**
     * 商品状态(1 上架，0 下架)
     */
    private Boolean saleStatus;

    /**
     * 是否可领优惠券(1 可，0 不可)
     */
    private Boolean coupon;

    /**
     * 是否是热门商品
     */
    private Boolean ifHot;

}