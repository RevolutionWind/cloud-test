package com.decent.common.mq.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jiangy
 * @date 2021/1/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsPriceCountDTO implements Serializable {

    private static final long serialVersionUID = 1305564293518720984L;
    /**
     * 用户编码
     */
    private String userCode;
    /**
     * 代理商编码
     */
    private String agentId;
    /**
     * 本次交易额
     */
    private BigDecimal transactionsPrice;

}
