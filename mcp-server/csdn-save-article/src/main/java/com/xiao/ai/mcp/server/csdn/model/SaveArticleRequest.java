package com.xiao.ai.mcp.server.csdn.model;

import lombok.Data;

@Data
public class SaveArticleRequest {
    private String article_id = "";
    private String title;
    private String description = "";
    private String content;
    private String tags;
    private String categories = "";
    private String type = "original";
    private Integer status = 0;
    private String read_type = "private";
    private Integer creation_statement = 0;
    private String reason = "";
    private String original_link = "";
    private Boolean authorized_status = false;
    private Boolean check_original = false;
    private String source = "pc_postedit";
    private Integer not_auto_saved = 1;
    private String creator_activity_id = "";
    private Object[] cover_images = new Object[0];
    private Integer cover_type = 1;
    private Integer vote_id = 0;
    private String resource_id = "";
    private Long scheduled_time = 0L;
    private Integer is_new = 1;
    private Integer sync_git_code = 0;
}