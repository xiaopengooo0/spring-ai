package com.xiao.std.ai.agent.app;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
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
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
        log.info("response: {}", JSON.toJSONString(response));
    }

    @Test
    public void message_stream() throws IOException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Flux<ChatResponse> stream = zhiPuAiChatModel.stream(new Prompt(
                UserMessage.builder().text(promoteResource.getContentAsString(StandardCharsets.UTF_8)).text("出生1999年11月23日，男").build()));
        stream.subscribe(response -> {
                    AssistantMessage output = response.getResult().getOutput();
                    log.info("测试结果(stream): {}", JSON.toJSONString(output));
                },
                throwable -> log.error("测试结果(stream)异常: {}", throwable.getMessage()),
                countDownLatch::countDown
        );
        
        // 等待异步操作完成，最多等待60秒
        countDownLatch.await(60, TimeUnit.SECONDS);
    }

    @Test
    public void message() throws IOException {
        SystemMessage systemMessage = SystemMessage.builder().text(promoteResource).build();
        ChatResponse response = zhiPuAiChatModel.call(new Prompt(systemMessage,UserMessage.builder().text("出生于公历1999年11月23日12点河南信阳，男").build()));
        log.info("测试结果: {}", JSON.toJSONString(response));
    }
}