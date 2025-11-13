package com.xiao.std.ai.agent.app;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;

/**
 * @Author: xiaopeng
 * @Description:
 * @DateTime: 2025/11/13 下午4:59 星期四
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AiAgentApiTest {

    @Autowired
    private ZhiPuAiChatModel zhiPuAiChatModel;

    @Value("classpath:data/promote.txt")
    private Resource promoteResource;

    @Value("classpath:data/dog.png")
    private Resource dogResource;

    @Test
    public void test_simple() throws IOException {
        ChatResponse response = zhiPuAiChatModel.call(new Prompt("1+1"));
        log.info("response: {}", response);
    }


    @Test
    public void image_test() throws IOException {

        UserMessage userMessage = UserMessage.builder()
                .text("请描述一下这张图片").media(
                        Media.builder()
                                .mimeType(MimeType.valueOf(MimeTypeUtils.IMAGE_PNG_VALUE))
                                .data(dogResource)
                                .build()
                ).build();

        ChatResponse response = zhiPuAiChatModel.call(new Prompt(userMessage, ChatOptions.builder()
                .model("glm-4v-flash").build()));
        log.info("response: {}", response);
    }
}