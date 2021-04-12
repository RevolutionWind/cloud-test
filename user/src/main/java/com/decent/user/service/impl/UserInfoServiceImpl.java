package com.decent.user.service.impl;

import com.decent.common.dto.CouponInfoDTO;
import com.decent.common.enums.ErrorCodeEnum;
import com.decent.common.exceptions.ErrorCodeException;
import com.decent.common.integral.entity.UserIntegral;
import com.decent.common.mq.entity.TransactionsPriceCountDTO;
import com.decent.common.pojo.MessageBean;
import com.decent.common.vo.ProductOrderVO;
import com.decent.common.vo.UserInfoVO;
import com.decent.user.client.CouponClient;
import com.decent.user.client.OrderClient;
import com.decent.user.client.UserIntegralClient;
import com.decent.user.dao.UserInfoDao;
import com.decent.user.service.UserInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author sunxy
 * @date 2021/1/10 10:07
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private OrderClient orderClient;
    @Resource
    private CouponClient couponClient;
    @Resource
    private UserIntegralClient userIntegralClient;

    @Override
    public void changeUserTransaction(TransactionsPriceCountDTO dto) {
        userInfoDao.updateUserTransactions(dto.getUserCode(), dto.getTransactionsPrice());
    }

    @Override
    public PageInfo<ProductOrderVO> listOrder(String userCode, PageInfo<?> pageInfo) {
        return orderClient.listOrder(userCode, pageInfo);
    }

    @Override
    public List<CouponInfoDTO> listUserNotCoupon(String userCode) {
        List<CouponInfoDTO> couponInfoDTOList = couponClient.listUserCoupon(userCode);
        couponInfoDTOList.addAll(couponClient.listUserNotCoupon(userCode));
        return couponInfoDTOList;
    }

    @Override
    public MessageBean getUserIntegral(String userCode) {
        return userIntegralClient.getUserIntegral(userCode);
    }

    @Override
    public UserInfoVO profile(String userCode) {
        UserInfoVO userInfoVO = Optional.ofNullable(userInfoDao.queryUserInfo(userCode))
                .orElseThrow(() -> new ErrorCodeException(ErrorCodeEnum.NO, "无用户信息"));
        MessageBean integral = userIntegralClient.getUserIntegral(userCode);
        userInfoVO.setIntegral(((UserIntegral) integral.getData()).getIntegral());
        return userInfoVO;
    }

}
