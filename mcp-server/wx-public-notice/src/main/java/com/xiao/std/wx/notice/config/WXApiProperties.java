package com.xiao.std.wx.notice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: xiaopeng
 * @Description: TODO
 * @DateTime: 2025/11/12 上午10:57 星期三
 **/
@ConfigurationProperties(prefix = "wx.config")
@Component
@Data
public class WXApiProperties {
    private String original_id;
    private String appId;
    private String appSecret;
    private String template_id;
    private String touser;
}
