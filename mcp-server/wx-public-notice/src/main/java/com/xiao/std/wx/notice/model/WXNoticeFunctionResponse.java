package com.xiao.std.wx.notice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WXNoticeFunctionResponse {

    @JsonProperty(required = true, value = "success")
    @JsonPropertyDescription("success")
    private boolean success;

}
