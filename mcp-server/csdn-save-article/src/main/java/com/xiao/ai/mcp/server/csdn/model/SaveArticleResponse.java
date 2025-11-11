package com.xiao.ai.mcp.server.csdn.model;

import lombok.Data;

@Data
public class SaveArticleResponse {
    private Integer code;
    private String traceId;
    private Data data;
    private String msg;

    @lombok.Data
    public static class Data {
        private String url;
        private Long article_id;
        private String title;
        private String description;
    }
}