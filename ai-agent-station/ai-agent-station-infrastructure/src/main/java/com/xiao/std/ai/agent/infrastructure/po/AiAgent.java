package com.xiao.std.ai.agent.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: xiaopeng
 * @Description: AI智能体配置表
 * @DateTime: 2025/11/14 下午2:33 星期五
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiAgent {
    /**
     * 主键
     */
    private Long id;

    /**
     * 智能体id
     */
    private String agentId;

    /**
     * 智能体名称
     */
    private String agentName;

    /**
     * 智能体描述
     */
    private String description;

    /**
     * 渠道类型(agent，chat_stream)
     */
    private String channel;

    /**
     * 智能体状态
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

