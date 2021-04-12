package com.decent.common.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jiangy
 * @date 2021/1/5
 */
@Data
public class GoodsStock implements Serializable {
    private static final long serialVersionUID = -5927857306620317739L;

    /**
     * da
     */
    private String name;
    /**
     * 产品编号
     */
    private Long goodsNo;
    /**
     * 库存数量
     */
    private Integer stockNum;
}
