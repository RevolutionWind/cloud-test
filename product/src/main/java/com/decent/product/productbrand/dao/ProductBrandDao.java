package com.decent.product.productbrand.dao;

import com.decent.common.enity.product.brand.ProductBrand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Max
 * @date 2021/1/10 9:52
 */
@Mapper
public interface ProductBrandDao {

    /**
     * 根据品牌分类id查询品牌
     *
     * @param typeId 分类id
     * @return list
     */
    @Select("select " +
            "id," +
            "type_id," +
            "brand_name," +
            "pic_url," +
            "pic_key," +
            "attribute," +
            "if_hot," +
            "sort," +
            "create_time " +
            "from product_brand " +
            "where " +
            "type_id=#{typeId} order by sort")
    List<ProductBrand> queryProductBrandList(@Param("typeId") Long typeId);
}