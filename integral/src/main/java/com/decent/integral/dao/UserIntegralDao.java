package com.decent.integral.dao;

import com.decent.common.integral.entity.UserIntegral;
import com.decent.common.integral.entity.UserIntegralRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @Description: <br/>
 * 用户积分sql
 * <p>
 * <br/>
 * @Author: zhangqi
 * @create 2021/1/7 21:10
 */
@Mapper
@Repository
public interface UserIntegralDao {

    /**
     * 查看用户的积分
     *
     * @param userCode 用户唯一标识
     * @return 结果
     */
    @Select(" SELECT " +
            "    ui.id, " +
            "    ui.user_code, " +
            "    ui.integral, " +
            "    ui.create_time, " +
            "    ui.update_time " +
            " FROM " +
            "    user_integral AS ui " +
            " WHERE  " +
            "    ui.user_code = #{userCode}")
    UserIntegral getUserIntegral(@Param("userCode") String userCode);

    /**
     * 增加修改积分记录
     *
     * @param userIntegralRecord 记录信息
     * @return 条数
     */
    @Insert(" INSERT INTO `user_integral_record` (`user_code`, `last_integral`, `change_integral`, `now_integral`, `create_time`, `update_time` ) " +
            " VALUES " +
            " ( #{dto.userCode}, #{dto.lastIntegral}, #{dto.changeIntegral}, #{dto.nowIntegral}, now(), now() ) ")
    int insertUserIntegralRecord(@Param("dto") UserIntegralRecord userIntegralRecord);

    /**
     * 更新用户的积分
     *
     * @param userCode 用户唯一标识
     * @param integral 改变的积分
     * @return 条数
     */
    @Update("UPDATE `user_integral`  " +
            "SET  " +
            " `integral` = #{integral}, " +
            " `update_time` = now()  " +
            "WHERE " +
            " `user_code` = #{userCode};")
    int updateUserIntegral(@Param("userCode") String userCode, @Param("integral") Integer integral);


}
