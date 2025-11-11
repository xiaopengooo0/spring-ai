package com.xiao.ai.mcp.server.csdn.mcp;

import com.xiao.ai.mcp.server.csdn.model.SaveArticleRequest;
import com.xiao.ai.mcp.server.csdn.model.SaveArticleResponse;
import com.xiao.ai.mcp.server.csdn.service.ICSDNService;
import com.xiao.ai.mcp.server.csdn.util.MarkdownConverter;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;

/**
 * @Author: xiaopeng
 * @Description:
 * @DateTime: 2025/11/11 下午2:27 星期二
 **/
@Service
public class SaveArticleMcpServer {

    private static final Logger log = LoggerFactory.getLogger(SaveArticleMcpServer.class);
    @Resource
    private ICSDNService csdnService;

    @Value("${csdn.cookie}")
    private String cookie;

    @Tool(name = "csdn_save_article", description = "csdn 保存文章")
    public SaveArticleResponse saveArticle(@ToolParam( description = "文章标题") String title,
                                           @ToolParam( description = "文章内容") String content,
                                           @ToolParam ( description = "文章标签，逗号分割") String tags,
                                           @ToolParam ( description = "文章简述") String description) throws IOException {
        SaveArticleRequest request = new SaveArticleRequest();
        request.setTitle(title);
        request.setContent(MarkdownConverter.convertToHtml(content));
        request.setTags(tags);
        request.setDescription(description);
        log.info("保存文章 {}", request);
        log.info("cookie{}", cookie);
        Response<SaveArticleResponse> execute = csdnService.saveArticle(cookie, request).execute();
        boolean successful = execute.isSuccessful();
        if (!successful) {
            log.error("保存文章失败 {}", execute.errorBody());
            throw new RuntimeException("保存文章失败"+execute.errorBody().string());
        }
        SaveArticleResponse body = execute.body() ;
        log.info("保存文章结果 响应码:{}, 响应体:{}", execute.code(), body);
        log.info("保存文章结果 {}", body);
        return body;
    }
}