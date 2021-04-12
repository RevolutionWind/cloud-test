package com.decent.product.bannel.controller;

import com.decent.common.enity.product.banner.IndexBanner;
import com.decent.product.bannel.service.IndexBannerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * IndexBanner的controller
 *
 * @author sunxy
 * @date 2020/12/22 19:10
 */
@RestController
@RequestMapping("/banner")
public class IndexBannerController {

    @Resource
    private IndexBannerService indexBannerService;

    @GetMapping("/home")
    public List<IndexBanner> getIndexBanners() {
        return indexBannerService.getIndexBanners();
    }

}
