package com.decent.common.enums;

/**
 * 全局http错误编码
 *
 * @author wangyx
 */
public enum ErrorCodeEnum {
    /**
     * 系统错误错误码
     */
    INVALID_PARAMS(9001, "参数有误"),
    INVALID_RSA_KEY(9002, "超时失效"),
    FREQUENTLY_REQUEST(9003, "操作频繁"),
    DATA_DOES_NOT_EXIST(9004, "数据不存在"),
    DATA_ALREADY_EXISTS(9005, "数据已存在"),
    ERROR(9999, "系统异常"),

    /**
     * 对外API接口(7开头)
     */
    FAIL(500, "请求失败"),
    SIGN_ERROR(1000, "签名异常"),
    USER_NOT_EXIST(5010, "用户不存在"),
    USER_LACK_OF_BALANCE(5011, "余额不足"),
    USER_BUCKLING_FAILURE(5012, "扣款失败"),
    USER_ADD_CONSUMPTION_DETAILS_FAILURE(5013, "新增消费明细失败"),
    USER_REFUND_FAILURE(5014, "退款失败"),
    USER_CONSUMPTION_INTERFACE_EXCEPTION(5015, "消费接口异常"),
    ORDER_REFUND_ERROR(5016, "订单退款失败"),
    /**
     * 泛用错误码
     */
    OK(200, "请求通过"),
    NO(201, "请求不通过"),
    // 针对外部提单接口的查单返回码
    NONE(203, "无效码"),
    DISPUTING(700, "纠纷中"),
    WAIT_SETTLEMENT(204, "待结算"),
    AUDIT(205, "审核中"),
    ;
    private final int code;
    private final String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
