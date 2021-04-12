package com.decent.product.bannel.service;


import com.decent.common.enity.product.banner.IndexBanner;

import java.util.List;

/**
 * @author sunxy
 * @date 2020/12/22 19:26
 */
public interface IndexBannerService {

    /**
     * 获取首页banner图
     *
     * @return List<IndexBanner>
     */
    List<IndexBanner> getIndexBanners();

}
