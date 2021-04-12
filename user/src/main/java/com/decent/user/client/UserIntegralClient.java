package com.decent.user.client;

import com.decent.common.pojo.MessageBean;
import com.decent.common.pojo.SimpleMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户积分
 *
 * @Author: zhangqi
 * @create 2021/1/9 10:04
 */
@FeignClient(value = "integral-service")
public interface UserIntegralClient {
    /**
     * 查看用户的积分
     *
     * @param userCode 用户唯一标识
     * @return 结果
     */
    @PostMapping("/integral/user/get/{userCode}")
    MessageBean getUserIntegral(@PathVariable(name = "userCode") String userCode);

    /**
     * 修改用户的积分
     *
     * @param userCode 用户Id
     * @param integral 改变的积分
     * @return 结果
     */
    @PostMapping("/integral/user/update")
    SimpleMessage updateUserIntegral(@RequestParam("userCode") String userCode,
                                     @RequestParam("integral") Integer integral);

}