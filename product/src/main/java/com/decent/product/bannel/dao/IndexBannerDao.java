package com.decent.product.bannel.dao;

import com.decent.common.enity.product.banner.IndexBanner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sunxy
 * @date 2020/12/22 19:47
 */
@Mapper
@Repository
public interface IndexBannerDao {

    /**
     * 获取banner图的标题和图片地址
     *
     * @return List<IndexBanner>
     */
    @Select("SELECT title, pic_url FROM index_banner")
    List<IndexBanner> getIndexBanners();

}
