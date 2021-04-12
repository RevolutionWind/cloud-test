package com.decent.product.product.service;

import com.decent.common.enity.product.product.ProductInfo;
import com.decent.product.product.dto.ProductDTO;
import com.github.pagehelper.PageInfo;

/**
 * @author sunxy
 * @date 2021/1/5 19:21
 */
public interface ProductService {

    /**
     * 获取商品详情
     *
     * @param goodsNo 商品编码
     * @return ProductInfo
     */
    ProductInfo getProductDetail(String goodsNo);


    /**
     * 查询产品
     * @param pageInfo 分页
     * @param productDTO dto
     * @return page
     */
    PageInfo<ProductInfo> queryProductList(PageInfo<?> pageInfo,ProductDTO productDTO);

}
