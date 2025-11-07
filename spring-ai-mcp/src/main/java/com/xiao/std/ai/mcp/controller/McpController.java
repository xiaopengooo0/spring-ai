package com.xiao.std.ai.mcp.controller;

import com.xiao.std.ai.mcp.service.McpService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xiaopeng
 * @Description: TODO
 * @DateTime: 2025/11/7 上午11:05 星期五
 **/
@RestController
public class McpController {

    @Resource
    private McpService mcpService;


    @RequestMapping("/mcp")
    public String mcp( String question){
      return  mcpService.ask( question);
    }
}