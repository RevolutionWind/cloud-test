package com.decent.product.product.controller;

import com.decent.common.enity.product.product.ProductInfo;
import com.decent.product.product.dto.ProductDTO;
import com.decent.product.product.service.ProductService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author sunxy
 * @date 2021/1/5 19:28
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    /**
     * 获取商品商品详情
     *
     * @param goodsNo 商品编码
     * @return ProductInfo
     */
    @RequestMapping("/details/{productId}")
    public ProductInfo getProductDetail(@PathVariable(name = "productId") String goodsNo) {
        return productService.getProductDetail(goodsNo);
    }


    /**
     * 获取商品列表
     *
     * @param pageInfo   分页
     * @param productDTO dto
     * @return ProductInfo
     */
    @RequestMapping("/list")
    public PageInfo<ProductInfo> queryList(PageInfo<?> pageInfo, ProductDTO productDTO) {
        return productService.queryProductList(pageInfo, productDTO);
    }
}
