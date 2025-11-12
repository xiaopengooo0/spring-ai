package com.xiao.std.wx.notice.model;

import lombok.Data;
import lombok.Getter;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

@Data
public class WxTemplateMessageDTO {

    private String touser;
    private String template_id;
    private String url = "https://weixin.qq.com";
    private Map<String, Map<String, String>> data = new HashMap<>();

    public WxTemplateMessageDTO(String touser, String template_id) {
        this.touser = touser;
        this.template_id = template_id;
    }

    public void put(TemplateKey key, String value) {
        data.put(key.getCode(), new HashMap<>() {
            @Serial
            private static final long serialVersionUID = 7092338402387318563L;

            {
                put("value", value);
            }
        });
    }

    public static void put(Map<String, Map<String, String>> data, TemplateKey key, String value) {
        data.put(key.getCode(), new HashMap<>() {
            @Serial
            private static final long serialVersionUID = 7092338402387318563L;

            {
                put("value", value);
            }
        });
    }


    @Getter
    public enum TemplateKey {
        platform("platform","平台"),
        subject("subject","主题"),
        description("description","简述");

        private String code;
        private String desc;

        TemplateKey(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }



}