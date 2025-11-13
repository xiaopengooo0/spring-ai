package com.xiao.std.ai.mcp.config;

import com.xiao.std.ai.mcp.tools.McpClientTools;
import io.micrometer.observation.ObservationRegistry;
import io.modelcontextprotocol.client.McpSyncClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.DefaultChatClientBuilder;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.List;

/**
 * @Author: xiaopeng
 * @Description: TODO
 * @DateTime: 2025/11/7 上午10:37 星期五
 **/
@Configuration
public class McpConfig {

    private static final Logger log = LoggerFactory.getLogger(McpConfig.class);

    @Bean
//    @Primary
    public ChatClient zhipuAiChatClient(ZhiPuAiChatModel zhiPuAiChatModel,
                                List<McpSyncClient> mcpSyncClients,
                                McpClientTools mcpClientTools) {
        DefaultChatClientBuilder clientBuilder = new DefaultChatClientBuilder(zhiPuAiChatModel, ObservationRegistry.NOOP, null);
        log.info("✅ 已加载 {} 个MCP同步客户端", mcpSyncClients.size());
        for (McpSyncClient syncClient : mcpSyncClients) {
            log.info("✅ 已加载MCP同步客户端: {}", syncClient.getClientInfo().name());
        }

      return   clientBuilder
                // 用于注册回调提供者，可以动态生成回调函数
            .defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpSyncClients))
                // 用于注册本地工具方法
            .defaultTools(mcpClientTools)
              .build();

    }



}