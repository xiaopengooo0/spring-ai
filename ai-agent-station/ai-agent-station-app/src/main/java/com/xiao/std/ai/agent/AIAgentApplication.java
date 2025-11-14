package com.xiao.std.ai.agent;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author: xiaopeng
 * @Description:
 * @DateTime: 2025/11/13 下午4:59 星期四
 **/
@SpringBootApplication
@Configurable
public class AIAgentApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AIAgentApplication.class, args);
        Object pgVectorStore = applicationContext.getBean("vectorStore");
        System.out.println(pgVectorStore);
    }
}
