package com.decent.integral.controller;

import com.decent.common.enums.ErrorCodeEnum;
import com.decent.common.pojo.MessageBean;
import com.decent.common.pojo.SimpleMessage;
import com.decent.integral.service.UserIntegralService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Description: <br/>
 * 用户积分
 * <p>
 * <br/>
 * @Author: sunxy
 * @create 2021/1/7 21:31
 */
@RestController
@RequestMapping("/integral/user")
public class UserIntegralController {

    @Resource
    private UserIntegralService userIntegralService;

    /**
     * 查看用户的积分
     *
     * @param userCode 用户唯一标识
     * @return 结果
     */
    @PostMapping("/get/{userCode}")
    public MessageBean getUserIntegral(@PathVariable("userCode") String userCode) {
        if (StringUtils.isBlank(userCode)) {
            return new MessageBean(ErrorCodeEnum.INVALID_PARAMS.getCode(), "用户唯一标识不合法");
        }
        return userIntegralService.getUserIntegral(userCode);
    }

    /**
     * 修改用户的积分
     *
     * @param userCode 用户Id
     * @param integral 改变的积分
     * @return 结果
     */
    @PostMapping("/update")
    public SimpleMessage updateUserIntegral(String userCode, Integer integral) {
        if (StringUtils.isBlank(userCode)) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "用户唯一标识不合法");
        }
        if (Objects.isNull(integral)) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "积分不合法");
        }
        return userIntegralService.updateUserIntegral(userCode, integral);
    }
}
