package com.decent.common.enity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author sunxy
 * @date 2021/1/2
 */
@Data
public class UserOrderProduct implements Serializable {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 商品图标
     */
    private String iconUrl;

    /**
     * 卡号
     */
    private String cardNumber;

    /**
     * 卡密
     */
    private String cardPwd;

    /**
     * 使用状态
     */
    private String useStatus;

    /**
     * 购买后有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime buyAfterEffectiveTime;

    /**
     * 使用须知
     */
    private String useNotice;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
