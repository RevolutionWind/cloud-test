package com.decent.common.coupon.enums;

import lombok.Getter;

/**
 * 优惠券使用状态
 *
 * @author sunxy
 * @date 2020/12/19
 */
@Getter
public enum UseStatusEnum {

    /**
     * 未使用
     */
    NOT_USED("未使用"),

    /**
     * 已使用
     */
    USED("已使用");

    private String status;

    UseStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
