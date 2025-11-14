package com.xiao.std.ai.agent.config;

import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: xiaopeng
 * @Description: TODO
 * @DateTime: 2025/11/14 下午4:18 星期五
 **/
@Configuration
public class AiAgentConfig {
    @Bean
    public TokenTextSplitter tokenTextSplitter() {
        return new TokenTextSplitter();
    }
}
