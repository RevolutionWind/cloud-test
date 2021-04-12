package com.decent.product.brandtype.controller;

import com.decent.common.enity.product.brandtype.BrandType;
import com.decent.product.brandtype.service.BrandTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Max
 * @date 2021/1/10 9:49
 */
@RestController
@RequestMapping("/brandType")
public class BrandTypeController {
    @Resource
    private BrandTypeService brandTypeService;

    /**
     * 查询所有的品牌属性列表
     *
     * @return list
     */
    @RequestMapping("/queryList")
    public List<BrandType> queryBrandTypeList() {
        return brandTypeService.queryBrandTypeList();
    }

}