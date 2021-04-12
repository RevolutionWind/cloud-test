package com.decent.common.enity.product.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author jiangy
 * @date 2021/1/5
 */
@Data
public class ProductInfo implements Serializable {
    private static final long serialVersionUID = 6413384737606687462L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 商品名
     */
    private String productName;

    /**
     * 分类id
     */
    private Long brandId;

    /**
     * 图片url
     */
    private String picUrl;

    /**
     * 图片key
     */
    private String picKey;

    /**
     * 售价
     */
    private BigDecimal sellPrice;

    /**
     * 官方原价
     */
    private BigDecimal officialPrice;

    /**
     * 商品状态(1 上架，0 下架)
     */
    private Boolean saleStatus;

    /**
     * 是否可领优惠券(1 可，0 不可)
     */
    private Boolean coupon;

    /**
     * 折扣(单位：百分比)
     */
    private Integer discount;

    /**
     * 是否是热门商品
     */
    private Boolean ifHot;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 购买后有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime buyAfterEffectiveTime;

    /**
     * 兑换后有效期天数
     */
    private Integer exchangeAfterEffectiveDay;

    /**
     * 使用须知
     */
    private String useNotice;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 橙券产品编号
     */
    private String goodsNo;

}
