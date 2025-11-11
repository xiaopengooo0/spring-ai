package com.xiao.ai.mcp.server.csdn;

import com.xiao.ai.mcp.server.csdn.mcp.SaveArticleMcpServer;
import com.xiao.ai.mcp.server.csdn.service.ICSDNService;
import okhttp3.OkHttpClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaopeng
 * @Description: TODO
 * @DateTime: 2025/11/11 上午11:54 星期二
 **/
@SpringBootApplication
public class CsdnSaveArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(CsdnSaveArticleApplication.class, args);
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Bean
    public Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://bizapi.csdn.net/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Bean
    public ICSDNService csdnService(Retrofit retrofit) {
        return retrofit.create(ICSDNService.class);
    }


    @Bean
    public ToolCallbackProvider csdnTools(SaveArticleMcpServer saveArticleMcpServer) {
        return MethodToolCallbackProvider.builder().toolObjects(saveArticleMcpServer).build();
    }

}