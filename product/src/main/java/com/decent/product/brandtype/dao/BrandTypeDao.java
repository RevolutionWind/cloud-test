package com.decent.product.brandtype.dao;

import com.decent.common.enity.product.brandtype.BrandType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Max
 * @date 2021/1/10 9:50
 */
@Mapper
public interface BrandTypeDao {

    /**
     * 查询所有的品牌属性列表
     *
     * @return list
     */
    @Select("select * from brand_type order by sort")
    List<BrandType> queryBrandTypeList();
}