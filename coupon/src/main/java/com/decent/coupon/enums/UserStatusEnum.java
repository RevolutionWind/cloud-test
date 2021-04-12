package com.decent.coupon.enums;


import lombok.Getter;

/**
 * @author sunxy
 * @date 2021/1/18 18:52
 */
@Getter
public enum UserStatusEnum {
    /**
     * 未使用
     */
    NOT_USED,
    /**
     * 已使用
     */
    USED,
    /**
     * 已失效
     */
    EXPIRED,
    ;

}
