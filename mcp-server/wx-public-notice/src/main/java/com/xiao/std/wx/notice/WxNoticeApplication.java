package com.xiao.std.wx.notice;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xiao.std.wx.notice.config.WXApiProperties;
import com.xiao.std.wx.notice.model.WXNoticeFunctionRequest;
import com.xiao.std.wx.notice.service.IWxApiService;
import com.xiao.std.wx.notice.service.McpWxNoticeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaopeng
 * @Description: TODO
 * @DateTime: 2025/11/11 下午5:06 星期二
 **/
@Slf4j
@SpringBootApplication
public class WxNoticeApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(WxNoticeApplication.class, args);
    }


    @Resource
    private WXApiProperties properties;


    @Bean
    public IWxApiService wxApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weixin.qq.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(IWxApiService.class);
    }

    @Bean(name = "wxAccessToken")
    public Cache<String, String> wxAccessToken() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS)
                .build();
    }

    @Bean
    public ToolCallbackProvider wxNoticeToolCallbackProvider(McpWxNoticeService mcpWxNoticeService) {
        return MethodToolCallbackProvider.builder().toolObjects(mcpWxNoticeService).build();
    }


    @Override
    public void run(String... args) {
        log.info("check properties ...");
        if (properties.getAppId() == null || properties.getAppSecret() == null || properties.getTouser() == null || properties.getTemplate_id() == null || properties.getOriginal_id() == null) {
            log.warn("weixin properties key is null, please set it in application.yml");
        } else {
            log.info("weixin properties key {}", properties.getAppId());
        }
    }

}
