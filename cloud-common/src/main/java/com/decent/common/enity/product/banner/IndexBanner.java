package com.decent.common.enity.product.banner;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author sunxy
 * @date 2020/12/22 19:19
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IndexBanner implements Serializable {

    private static final long serialVersionUID = 2609271326842523975L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 图片链接
     */
    private String picUrl;
    /**
     * banner图名称
     */
    private String title;
    /**
     * 图片key
     */
    private String picKey;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

}
