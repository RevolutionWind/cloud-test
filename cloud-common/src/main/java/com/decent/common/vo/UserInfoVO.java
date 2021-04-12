package com.decent.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author : yum
 * @date : by 2021/1/11
 * @description :
 */
@Data
public class UserInfoVO implements Serializable {
    private static final long serialVersionUID = 5965727995860316984L;

    /**
     * 用户唯一标识
     */
    private String userCode;
    /**
     * 手机号
     */
    private String userPhoneNo;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime registerTime;
    /**
     * 总交易额
     */
    private BigDecimal transactionPrice;
    /**
     * 优惠券数量
     */
    private Long couponCount;
    /**
     * 积分
     */
    private Integer integral;

}