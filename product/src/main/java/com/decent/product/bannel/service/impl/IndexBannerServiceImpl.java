package com.decent.product.bannel.service.impl;

import com.decent.common.enity.product.banner.IndexBanner;
import com.decent.product.bannel.dao.IndexBannerDao;
import com.decent.product.bannel.service.IndexBannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunxy
 * @date 2020/12/22 19:28
 */
@Service
@Slf4j
public class IndexBannerServiceImpl implements IndexBannerService {

    @Resource
    private IndexBannerDao indexBannerDao;

    /**
     * 获取首页banner图
     *
     * @return List<IndexBanner>
     */
    @Override
    public List<IndexBanner> getIndexBanners() {
        return indexBannerDao.getIndexBanners();
    }
}
