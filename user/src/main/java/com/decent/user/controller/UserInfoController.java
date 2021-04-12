package com.decent.user.controller;

import com.alibaba.nacos.common.utils.MD5Utils;
import com.decent.common.dto.CouponInfoDTO;
import com.decent.common.enums.ErrorCodeEnum;
import com.decent.common.pojo.MessageBean;
import com.decent.common.pojo.Response;
import com.decent.common.pojo.SimpleMessage;
import com.decent.common.vo.ProductOrderVO;
import com.decent.common.vo.UserInfoVO;
import com.decent.user.service.UserInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author : yum
 * @date : by 2021/1/11
 * @description : 描述
 */
@RestController
@RequestMapping("user")
public class UserInfoController {
    private final static String USER_AUTH_REDIS_KEY = "userAuthRedisKey";
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 授权登录入口
     *
     * @param userCode 授权码
     * @return SimpleMessage
     */
    @RequestMapping("auth/auto/login")
    public SimpleMessage authLogin(String userCode) {
        String userKey = USER_AUTH_REDIS_KEY + userCode;
        String key = MD5Utils.md5Hex(userKey, "utf-8");
        stringRedisTemplate.opsForValue().set(key, userCode, 1, TimeUnit.DAYS);
        return new SimpleMessage(ErrorCodeEnum.OK, key);
    }

    /**
     * 我的订单
     *
     * @param userCode 用户唯一编码
     * @param pageInfo 分页实体
     * @return 订单
     */
    @RequestMapping("listOrder")
    public PageInfo<ProductOrderVO> listOrder(String userCode, PageInfo<?> pageInfo) {
        return userInfoService.listOrder(userCode, pageInfo);
    }

    /**
     * 获取优惠券
     *
     * @param userCode 用户唯一编码
     * @return 优惠券
     */
    @GetMapping("listUserNotCoupon/{userCode}")
    public List<CouponInfoDTO> listUserNotCoupon(@PathVariable String userCode) {
        return userInfoService.listUserNotCoupon(userCode);
    }

    /**
     * 获取用户积分
     *
     * @param userCode 用户唯一编码
     * @return 积分
     */
    @GetMapping("getUserIntegral/{userCode}")
    public MessageBean getUserIntegral(@PathVariable String userCode) {
        return userInfoService.getUserIntegral(userCode);
    }

    /**
     * 获取用户信息
     *
     * @param userCode 用户唯一编码
     * @return 用户信息
     */
    @GetMapping("profile/{userCode}")
    public Response<UserInfoVO> profile(@PathVariable String userCode) {
        Response<UserInfoVO> response = new Response<>();
        response.setData(userInfoService.profile(userCode));
        return response;
    }
}
