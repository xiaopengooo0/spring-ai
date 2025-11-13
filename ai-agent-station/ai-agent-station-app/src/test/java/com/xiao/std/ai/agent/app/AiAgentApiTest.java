package com.xiao.std.ai.agent.app;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @Author: xiaopeng
 * @Description:
 * @DateTime: 2025/11/13 下午4:59 星期四
 **/
@SpringBootApplication
@RunWith(SpringRunner .class)
@Slf4j
public class AiAgentApiTest {

    @Autowired
    private ZhiPuAiChatModel zhiPuAiChatModel;

    @Value("classpath:ddd-promote.txt")
    private Resource articlePromptWordsResource;

    @Test
    public void test_simple() throws IOException {
        ChatResponse response = zhiPuAiChatModel.call(new Prompt("1+1",
                ZhiPuAiChatOptions.builder()
                        .model("glm-4.5-flash").build()));
        log.info("response: {}", response);

    }
}
