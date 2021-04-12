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
 * 用户积分记录
 * <p>
 * <br/>
 * @Author: zhangqi
 * @create 2021/1/7 21:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserIntegralRecord implements Serializable {
    private static final long serialVersionUID = -8955639957577217665L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户唯一标识
     */
    private String userCode;

    /**
     * 上一次的积分
     */
    private Integer lastIntegral;

    /**
     * 改变的积分
     */
    private Integer changeIntegral;

    /**
     * 现在的积分
     */
    private Integer nowIntegral;

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
