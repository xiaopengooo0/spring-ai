package com.xiao.std.ai.mcp.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * @Author: xiaopeng
 * @Description: TODO
 * @DateTime: 2025/11/7 上午11:56 星期五
 **/
@Service
public class McpService {

    private final ChatClient chatClient;

    public McpService(ChatClient.Builder builder) {
        this.chatClient = builder.build();

    }

    public String ask(String question) {
        return chatClient.prompt(question).call().content();
    }

}
