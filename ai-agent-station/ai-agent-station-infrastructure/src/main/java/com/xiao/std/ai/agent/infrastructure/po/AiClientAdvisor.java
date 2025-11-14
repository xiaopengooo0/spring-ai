package com.xiao.std.ai.agent.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: xiaopeng
 * @Description: 顾问配置表
 * @DateTime: 2025/11/14 下午2:41 星期五
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiClientAdvisor {
    /**
     * 主键
     */
    private Long id;
    /**
     * 顾问id
     */
    private String advisorId;

    /**
     * 顾问名称
     */
    private String advisorName;
    /**
     * 顾问类型
     */
    private String advisorType;
    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * 扩展参数配置，json 记录
     */
    private String extParam;

    /**
     * 状态(0:禁用,1:启用)
     */
    private Integer status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
