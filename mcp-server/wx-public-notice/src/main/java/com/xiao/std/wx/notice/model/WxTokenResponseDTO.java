package com.xiao.std.wx.notice.model;

import lombok.Data;

@Data
public class WxTokenResponseDTO {

    private String access_token;
    private int expires_in;
    private String errcode;
    private String errmsg;
}