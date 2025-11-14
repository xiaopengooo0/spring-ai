package com.xiao.ai.mcp.server.sse.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

/**
 * @Author: xiaopeng
 * @Description: TODO
 * @DateTime: 2025/11/14 上午11:00 星期五
 **/
@Component
public class SseService {

    @Tool(name = "get_time", description = "获取当前时间")
    public String getTime() {
        // 获取当前时间
        return "当前时间是：" + java.time.LocalDateTime.now();
    }
}
