package com.decent.product.brandtype.service;

import com.decent.common.enity.product.brandtype.BrandType;

import java.util.List;

/**
 * @author Max
 * @date 2021/1/10 9:50
 */
public interface BrandTypeService {

    /**
     * 查询所有的品牌属性列表
     * @return list
     */
    List<BrandType>  queryBrandTypeList();
}