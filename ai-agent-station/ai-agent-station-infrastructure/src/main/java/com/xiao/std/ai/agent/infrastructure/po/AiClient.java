package com.xiao.std.ai.agent.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: xiaopeng
 * @Description: AI客户端配置表
 * @DateTime: 2025/11/14 下午2:40 星期五
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiClient {
    /**
     * 主键
     */
    private Long id;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
