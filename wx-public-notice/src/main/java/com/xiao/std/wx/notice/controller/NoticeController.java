package com.xiao.std.wx.notice.controller;

import com.xiao.std.wx.notice.model.MessageTextEntity;
import com.xiao.std.wx.notice.util.SignatureUtil;
import com.xiao.std.wx.notice.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xiaopeng
 * @Description: TODO
 * @DateTime: 2025/11/11 下午5:07 星期二
 **/
@RestController
@RequestMapping("api/wx/portal")
@Slf4j
public class NoticeController {


    @Value("${wx.config.originalId}")
    private String originalid;

    @Value("${wx.config.token}")
    private String token;

    @PostMapping(value = "/receive",produces = "application/xml; charset=UTF-8")
    public String receive(@RequestBody String requestBody,
                          @RequestParam("signature") String signature,
                          @RequestParam("timestamp") String timestamp,
                          @RequestParam("nonce") String nonce,
                          @RequestParam("openid") String openid,
                          @RequestParam(name = "encrypt_type", required = false) String encType,
                          @RequestParam(name = "msg_signature", required = false) String msgSignature) {

        try{
            log.info("接收微信公众号信息请求{}开始 {}", openid, requestBody);
            // 消息转换
            MessageTextEntity message = XmlUtil.xmlToBean(requestBody, MessageTextEntity.class);
            if ("event".equals(message.getMsgType()) && "SCAN".equals(message.getEvent())) {
                return buildMessageTextEntity(openid, "登录成功");
            }
            return buildMessageTextEntity(openid, "你好，" + message.getContent());
        }catch (Exception e){
            log.error("接收微信公众号信息请求{}失败", openid, e);
        }
        return "index";
    }
    private String buildMessageTextEntity(String openid, String content) {
        MessageTextEntity res = new MessageTextEntity();
        // 公众号分配的ID
        res.setFromUserName(originalid);
        res.setToUserName(openid);
        res.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
        res.setMsgType("text");
        res.setContent(content);
        return XmlUtil.beanToXml(res);
    }


    @GetMapping(value = "/receive", produces = "text/plain;charset=utf-8")
    public String validate(@RequestParam(value = "signature", required = false) String signature,
                           @RequestParam(value = "timestamp", required = false) String timestamp,
                           @RequestParam(value = "nonce", required = false) String nonce,
                           @RequestParam(value = "echostr", required = false) String echostr) {
        try {
            log.info("微信公众号验签信息开始 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
            if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
                throw new IllegalArgumentException("请求参数非法，请核实!");
            }
            boolean check = SignatureUtil.check(token, signature, timestamp, nonce);
            log.info("微信公众号验签信息完成 check：{}", check);
            if (!check) {
                return null;
            }
            return echostr;
        } catch (Exception e) {
            log.error("微信公众号验签信息失败 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr, e);
            return null;
        }
    }
}
