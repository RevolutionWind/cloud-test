package com.decent.product.productbrand.controller;

import com.decent.common.enity.product.brand.ProductBrand;
import com.decent.common.enums.ErrorCodeEnum;
import com.decent.common.exceptions.ErrorCodeException;
import com.decent.product.productbrand.service.ProductBrandService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author Max
 * @date 2021/1/10 9:51
 */
@RestController
@RequestMapping("/brand")
public class ProductBrandController {

    @Resource
    private ProductBrandService productBrandService;

    /**
     * 根据品牌分类查询品牌
     *
     * @param typeId 品牌分类id
     * @return list
     */
    @RequestMapping("/list")
    public List<ProductBrand> queryProductBrandList(Long typeId) {
        if (Objects.isNull(typeId)) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS, "请选择品牌分类");
        }
        return productBrandService.queryProductBrandList(typeId);
    }
}