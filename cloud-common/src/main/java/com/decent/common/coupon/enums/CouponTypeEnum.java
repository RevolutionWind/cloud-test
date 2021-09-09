package com.decent.common.coupon.enums;

import lombok.Getter;

/**
 * 优惠券类型
 *
 * @author sunxy
 * @date 2020/12/19
 */
@Getter
public enum CouponTypeEnum {

    /**
     * 免费
     */
    FREE("免费"),

    /**
     * 满减
     */
    FULL_REDUCTION("满减");

    private String type;

    CouponTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
