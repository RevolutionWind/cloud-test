package com.decent.integral.service.impl;

import com.decent.common.enums.ErrorCodeEnum;
import com.decent.common.exceptions.ErrorCodeException;
import com.decent.common.integral.entity.UserIntegral;
import com.decent.common.integral.entity.UserIntegralRecord;
import com.decent.common.pojo.MessageBean;
import com.decent.common.pojo.SimpleMessage;
import com.decent.integral.dao.UserIntegralDao;
import com.decent.integral.service.UserIntegralService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Description: <br/>
 * 用户积分服务 实现
 * <p>
 * <br/>
 * @Author: zhangqi
 * @create 2021/1/7 21:33
 */
@Slf4j
@Service
public class UserIntegralServiceImpl implements UserIntegralService {
    @Resource
    private UserIntegralDao userIntegralDao;

    /**
     * 查看用户的积分
     *
     * @param userCode 用户唯一标识
     * @return 结果
     */
    @Override
    public MessageBean getUserIntegral(String userCode) {
        UserIntegral userIntegral = userIntegralDao.getUserIntegral(userCode);
        if (Objects.isNull(userIntegral)) {
            return new MessageBean(ErrorCodeEnum.NO.getCode(), "没有查询到用户积分");
        }
        return new MessageBean(userIntegral);
    }

    /**
     * 修改用户的积分
     *
     * @param userCode 用户唯一标识
     * @param integral 改变的积分
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleMessage updateUserIntegral(String userCode, Integer integral) {
        UserIntegral userIntegral = userIntegralDao.getUserIntegral(userCode);
        if (Objects.isNull(userIntegral)) {
            throw new ErrorCodeException(ErrorCodeEnum.NO, "没有查询到用户积分");
        }
        int row = userIntegralDao.updateUserIntegral(userCode, integral);
        if (row != 1) {
            throw new ErrorCodeException(ErrorCodeEnum.NO, "更新失败");
        }
        // 新积分
        int nowIntegral = integral + userIntegral.getIntegral();
        row = userIntegralDao.insertUserIntegralRecord(UserIntegralRecord.builder().changeIntegral(integral)
                .lastIntegral(userIntegral.getIntegral()).nowIntegral(nowIntegral).build());
        if (row != 1) {
            throw new ErrorCodeException(ErrorCodeEnum.NO, "插入记录失败");
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }
}
