package com.xiao.std.ai.agent.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: xiaopeng
 * @Description: 智能体任务调度配置表
 * @DateTime: 2025/11/14 下午2:38 星期五
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiAgentTaskSchedule {
    /**
     * 主键
     */
    private Long id;

    /**
     * 智能体id
     */
    private String agentId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 调度表达式
     */
    private String cronExpression;

    /**
     * 任务入参配置(JSON格式)
     */
    private String taskParam;

    /**
     * 任务状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
