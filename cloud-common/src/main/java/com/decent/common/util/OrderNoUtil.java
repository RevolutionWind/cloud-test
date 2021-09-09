package com.decent.common.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 订单编号工具
 *
 * @author sunxy
 */
public final class OrderNoUtil {

    public static final String CARD_ORDER_NO_PREFIX = "CARD";

    /**
     * 生成订单号
     *
     * @param pre 前缀
     * @return 订单号
     */
    public static String generateOrderNo(String pre) {
        int randomLength = 6;
        return pre + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) +
                RandomStringUtils.randomNumeric(randomLength);
    }

}
