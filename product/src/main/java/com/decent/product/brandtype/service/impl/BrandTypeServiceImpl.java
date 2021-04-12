package com.decent.product.brandtype.service.impl;

import com.decent.common.enity.product.brandtype.BrandType;
import com.decent.product.brandtype.dao.BrandTypeDao;
import com.decent.product.brandtype.service.BrandTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Max
 * @date 2021/1/10 9:50
 */
@Service
public class BrandTypeServiceImpl implements BrandTypeService {

    @Resource
    private BrandTypeDao brandTypeDao;

    /**
     * 查询所有的品牌属性列表
     *
     * @return list
     */
    @Override
    public List<BrandType> queryBrandTypeList() {
        return brandTypeDao.queryBrandTypeList();
    }
}