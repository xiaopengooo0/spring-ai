package com.xiao.std.wx.notice.service;

import com.xiao.std.wx.notice.model.WXNoticeFunctionRequest;
import com.xiao.std.wx.notice.model.WXNoticeFunctionResponse;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: xiaopeng
 * @Description:
 * @DateTime: 2025/11/12 上午11:30 星期三
 **/
@Component
public class McpWxNoticeService {

    private static final Logger log = LoggerFactory.getLogger(McpWxNoticeService.class);
    @Resource
    private WxNoticeService WxNoticeService;

    @Tool(name = "wx-notice", description = "微信通知服务")
    public WXNoticeFunctionResponse wxNotice(WXNoticeFunctionRequest request) throws IOException {
        log.info("微信消息通知，平台:{} 主题:{} 描述:{}", request.getPlatform(), request.getSubject(), request.getDescription());
        return WxNoticeService.wxNotice(request);
    }
}
