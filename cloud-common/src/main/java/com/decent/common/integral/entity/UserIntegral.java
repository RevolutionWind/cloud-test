package com.decent.common.integral.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: <br/>
 * 用户优惠券
 * <p>
 * <br/>
 * @Author: sunxy
 * @create 2021/1/7 21:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserIntegral implements Serializable {
    private static final long serialVersionUID = -3248865264195362424L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户唯一标识
     */
    private String userCode;

    /**
     * 用户积分
     */
    private Integer integral;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
