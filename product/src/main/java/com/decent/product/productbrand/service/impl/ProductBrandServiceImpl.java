package com.decent.product.productbrand.service.impl;

import com.decent.common.enity.product.brand.ProductBrand;
import com.decent.product.productbrand.dao.ProductBrandDao;
import com.decent.product.productbrand.service.ProductBrandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Max
 * @date 2021/1/10 9:53
 */
@Service
public class ProductBrandServiceImpl implements ProductBrandService {

    @Resource
    private ProductBrandDao productBrandDao;

    /**
     * 根据品牌分类查询品牌
     *
     * @param typeId 品牌分类id
     * @return list
     */
    @Override
    public List<ProductBrand> queryProductBrandList(Long typeId) {
        return productBrandDao.queryProductBrandList(typeId);
    }
}