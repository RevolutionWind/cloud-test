package com.decent.common.enity.product.brandtype;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 品牌属性列表
 *
 * @author sunxy
 * @date 2021/1/10 10:20
 */
@Data
public class BrandType implements Serializable {

    private static final long serialVersionUID = -3191381917359279158L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 品牌类型名称
     */
    private String typeName;

    /**
     * 图片url
     */
    private String iconUrl;

    /**
     * 图片key
     */
    private String iconKey;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 排序
     */
    private Integer sort;

    public BrandType() {
    }
}