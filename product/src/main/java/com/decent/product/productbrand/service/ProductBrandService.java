package com.decent.product.productbrand.service;

import com.decent.common.enity.product.brand.ProductBrand;

import java.util.List;

/**
 * @author Max
 * @date 2021/1/10 9:53
 */
public interface ProductBrandService {

    /**
     * 根据品牌分类查询品牌
     * @param typeId 品牌分类id
     * @return list
     */
    List<ProductBrand> queryProductBrandList(Long typeId);
}