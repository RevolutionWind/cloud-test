package com.decent.product.product.dao;

import com.decent.common.enity.product.product.ProductInfo;
import com.decent.product.product.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunxy
 * @date 2021/1/5 19:31
 */
@Mapper
public interface ProductDao {

    /**
     * 获取商品详情
     *
     * @param goodsNo 商品编码
     * @return ProductInfo
     */
    @Select("SELECT * FROM product_info WHERE goods_no = #{goodsNo} ")
    ProductInfo getProductDetail(@Param("goodsNo") String goodsNo);


    /**
     * 根据dto查询产品列表
     *
     * @param productDTO 查询条件
     * @return
     */
    @Select("<script>" +
            "select " +
            "id," +
            "product_name," +
            "brand_id, " +
            "pic_url, " +
            "pic_key, " +
            "sell_price, " +
            "official_price, " +
            "sale_status, " +
            "coupon, " +
            "discount, " +
            "if_hot, " +
            "sort, " +
            "buy_after_effective_time, " +
            "exchange_after_effective_day, " +
            "exchange_after_effective_day, " +
            "use_notice, " +
            "goods_no " +
            " from product_info " +
            "<where>" +
            "<if test = \"dto.productName != null and dto.productName!=''\" >" +
            " and product_name = #{dto.productName} " +
            "</if>" +
            "<if test = 'dto.productName != null' >" +
            " and brand_id = #{dto.brandId} " +
            "</if>" +
            "<if test = 'dto.saleStatus != null' >" +
            " and sale_status = #{dto.saleStatus} " +
            "</if>" +
            "<if test = 'dto.coupon != null' >" +
            " and coupon = #{dto.coupon} " +
            "</if>" +
            "<if test = 'dto.ifHot != null' >" +
            " and if_hot = #{dto.ifHot} " +
            "</if>" +
            "</where>" +
            "order by sort" +
            "</script>")
    List<ProductInfo> queryProductList(@Param("dto") ProductDTO productDTO);
}
