package com.xiao.std.ai.agent.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: xiaopeng
 * @Description: 智能体-客户端关联表
 * @DateTime: 2025/11/14 下午2:36 星期五
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiAgentFlowConfig {
    /**
     * 主键
     */
    private Long id;
    /**
     * 智能体id
     */
    private String agentId;

    /**
     * 客户端id
     */
    private Long clientId;

    /**
     * 序列号(执行顺序)
     */
    private Integer sequence;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
