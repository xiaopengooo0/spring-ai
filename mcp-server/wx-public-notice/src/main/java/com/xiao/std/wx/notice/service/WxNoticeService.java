package com.xiao.std.wx.notice.service;

import com.google.common.cache.Cache;
import com.xiao.std.wx.notice.config.WXApiProperties;
import com.xiao.std.wx.notice.model.WXNoticeFunctionRequest;
import com.xiao.std.wx.notice.model.WXNoticeFunctionResponse;
import com.xiao.std.wx.notice.model.WxTemplateMessageDTO;
import com.xiao.std.wx.notice.model.WxTokenResponseDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import retrofit2.Call;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class WxNoticeService {

    @Resource
    private WXApiProperties properties;

    @Resource
    private IWxApiService wxApiService;

    @Resource
    private Cache<String, String> wxAccessToken;

    public WXNoticeFunctionResponse wxNotice(WXNoticeFunctionRequest request) throws IOException {
        // 1. 获取 accessToken
        String accessToken = wxAccessToken.getIfPresent(properties.getAppId());
        if (null == accessToken) {
            Call<WxTokenResponseDTO> call = wxApiService.getToken("client_credential", properties.getAppId(), properties.getAppSecret());
            WxTokenResponseDTO wxTokenResponseDTO = call.execute().body();
            assert wxTokenResponseDTO != null;
            accessToken = wxTokenResponseDTO.getAccess_token();
            wxAccessToken.put(properties.getAppId(), accessToken);
        }

        // 2. 发送模板消息
        Map<String, Map<String, String>> data = new HashMap<>();
        WxTemplateMessageDTO.put(data, WxTemplateMessageDTO.TemplateKey.platform, request.getPlatform());
        WxTemplateMessageDTO.put(data, WxTemplateMessageDTO.TemplateKey.subject, request.getSubject());
        WxTemplateMessageDTO.put(data, WxTemplateMessageDTO.TemplateKey.description, request.getDescription());

        WxTemplateMessageDTO templateMessageDTO = new WxTemplateMessageDTO(properties.getTouser(), properties.getTemplate_id());
        templateMessageDTO.setUrl(request.getJumpUrl());
        templateMessageDTO.setData(data);

        Call<Void> call = wxApiService.sendMessage(accessToken, templateMessageDTO);
        call.execute();

        WXNoticeFunctionResponse weiXinNoticeFunctionResponse = new WXNoticeFunctionResponse();
        weiXinNoticeFunctionResponse.setSuccess(true);

        return weiXinNoticeFunctionResponse;
    }

}