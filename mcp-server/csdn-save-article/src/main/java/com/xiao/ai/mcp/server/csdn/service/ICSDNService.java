package com.xiao.ai.mcp.server.csdn.service;

import com.xiao.ai.mcp.server.csdn.model.SaveArticleRequest;
import com.xiao.ai.mcp.server.csdn.model.SaveArticleResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ICSDNService {

    @Headers({
            "ACCEPT: application/json, text/plain, */*",
            "ACCEPT-LANGUAGE:zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6",
            "CONTENT-TYPE: application/json;",
            "ORIGIN: HTTPS://mp.csdn.net",
            "REFERER: https://mp.csdn.net/",
            "SEC-CH-UA:\"Chromium\";v=\"142\", \"Microsoft Edge\";v=\"142\", \"Not_A Brand\";v=\"99\"",
            "SEC-CH-UA-mobile:?0",
            "SEC-CH-UA-PLATFORM: \"Windows\"",
            "SEC-FETCH-DEST: empty",
            "SEC-FETCH-MODE: cors",
            "SEC-FETCH-SITE: same-site",
            "USER-AGENT:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36 Edg/142.0.0.0",
            "X-CA-KEY: 203803574",
            "X-CA-NONCE: 59fdfb04-1e1d-490a-9b4b-0354095cdf3b",
            "X-CA-SIGNATURE: FN2aApXbpRfLGV1uKZ2Imi5iqWG5zR86NjUgqwAhKmo=",
            "X-CA-SIGNATURE-HEADERS: x-ca-key,x-ca-nonce",
    })
    @POST("blog-console-api/v1/postedit/saveArticle")
    Call<SaveArticleResponse> saveArticle(
            @Header("Cookie") String cookie,
            @Body SaveArticleRequest request
    );
}