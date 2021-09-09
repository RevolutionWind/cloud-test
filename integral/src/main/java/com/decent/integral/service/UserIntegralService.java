package com.decent.integral.service;

import com.decent.common.pojo.MessageBean;
import com.decent.common.pojo.SimpleMessage;

/**
 * @Description: <br/>
 * 用户积分服务 接口
 * <p>
 * <br/>
 * @Author: sunxy
 * @create 2021/1/7 21:33
 */
public interface UserIntegralService {
    /**
     * 查看用户的积分
     *
     * @param userCode 用户唯一标识
     * @return 结果
     */
    MessageBean getUserIntegral(String userCode);

    /**
     * 修改用户的积分
     *
     * @param userCode 用户唯一标识
     * @param integral 改变的积分
     * @return 结果
     */
    SimpleMessage updateUserIntegral(String userCode, Integer integral);
}
