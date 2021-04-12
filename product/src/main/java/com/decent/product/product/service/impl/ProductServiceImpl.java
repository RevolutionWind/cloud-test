package com.decent.product.product.service.impl;

import com.decent.common.enity.product.product.ProductInfo;
import com.decent.product.product.dao.ProductDao;
import com.decent.product.product.dto.ProductDTO;
import com.decent.product.product.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author sunxy
 * @date 2021/1/5 19:34
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductDao productDao;

    /**
     * 获取商品详情
     *
     * @param goodsNo 商品编码
     * @return ProductInfo
     */
    @Override
    public ProductInfo getProductDetail(String goodsNo) {
        return productDao.getProductDetail(goodsNo);
    }

    /**
     * 查询产品
     *
     * @param pageInfo   分页
     * @param productDTO dto
     * @return page
     */
    @Override
    public PageInfo<ProductInfo> queryProductList(PageInfo<?> pageInfo, ProductDTO productDTO) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        return new PageInfo<>(productDao.queryProductList(productDTO));
    }
}
