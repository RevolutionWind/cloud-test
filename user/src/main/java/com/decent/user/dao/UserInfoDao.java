package com.decent.user.dao;

import com.decent.common.vo.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * @author sunxy
 * @date 2021/1/10 10:12
 */
@Mapper
public interface UserInfoDao {

    /**
     * 更新用户的交易额
     *
     * @param userCode            用户code
     * @param changedTransactions 交易额
     */
    @Update("UPDATE user_info " +
            "SET transactions_price = transactions_price + #{changed} " +
            "WHERE user_code = #{userCode}")
    void updateUserTransactions(@Param("userCode") String userCode,
                                @Param("changed") BigDecimal changedTransactions);

    /**
     * 查询用户属性
     *
     * @param userCode 用户唯一标识
     * @return 用户信息
     */
    @Select("select u.user_code, i.transactions_price, i.coupon_count, u.user_phone_no, " +
            "u.user_name, u.create_time as registerTime " +
            "from user u inner join user_info i on u.user_code = i.user_code " +
            "where u.user_code = #{userCode}")
    UserInfoVO queryUserInfo(@Param("userCode") String userCode);
}
