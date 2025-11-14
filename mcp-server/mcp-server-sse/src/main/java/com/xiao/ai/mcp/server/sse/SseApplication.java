package com.xiao.ai.mcp.server.sse;

import com.xiao.ai.mcp.server.sse.service.SseService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @Author: xiaopeng
 * @Description: TODO
 * @DateTime: 2025/11/14 上午10:57 星期五
 **/
@SpringBootApplication
public class SseApplication {
    public static void main(String[] args) {
        SpringApplication.run(SseApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider timeTool(SseService sseService){
        return MethodToolCallbackProvider.builder().toolObjects(sseService).build();
    }
}
