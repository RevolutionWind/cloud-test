package com.decent.order.client;

import com.decent.common.enity.product.product.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sunxy
 * @date 2021/1/5 20:09
 */
@FeignClient(value = "product-service")
@RequestMapping("/product")
public interface ProductClient {

    /**
     * 获取商品详情
     *
     * @param goodsNo 商品编号
     * @return ProductInfo
     */
    @GetMapping("/details/{productId}")
    ProductInfo getProductDetail(@PathVariable(name = "productId") String goodsNo);

}