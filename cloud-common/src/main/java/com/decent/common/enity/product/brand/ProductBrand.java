package com.decent.common.enity.product.brand;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author sunxy
 * @date 2021/1/5 19:11
 */
@Data
public class ProductBrand implements Serializable {

    private static final long serialVersionUID = 5062502195498800741L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 分类id
     */
    private Long typeId;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 图片地址
     */
    private String picUrl;
    /**
     * 图片key
     */
    private String picKey;
    /**
     * 品牌属性
     */
    private String attribute;
    /**
     * 是否是热门商品
     */
    private Boolean ifHot;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}

