package com.decent.common.vo;

import com.decent.common.enity.ProductOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author sunxy
 * @date 2021/1/6
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProductOrderVO extends ProductOrder {
    private static final long serialVersionUID = -2809871348420759305L;
    /**
     * 品牌名称
     */
    private String brandName;
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
    private String buyAfterEffectiveTime;
    /**
     * 使用须知
     */
    private String useNotice;
}