package com.xiao.ai.mcp.server.csdn;

import com.xiao.ai.mcp.server.csdn.mcp.SaveArticleMcpServer;
import com.xiao.ai.mcp.server.csdn.model.SaveArticleRequest;
import com.xiao.ai.mcp.server.csdn.model.SaveArticleResponse;
import com.xiao.ai.mcp.server.csdn.service.ICSDNService;
import com.xiao.ai.mcp.server.csdn.util.MarkdownConverter;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import retrofit2.Response;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Autowired
    private ICSDNService csdnService;

    @Resource
    private SaveArticleMcpServer saveArticleMcpServer;

    @Test
    public void testSaveArticle() throws IOException {
        // 构造请求参数
        SaveArticleRequest request = new SaveArticleRequest();
        request.setTitle("测试文章1");
        request.setContent("<p>1111111</p>\n");
        request.setTags("java");

        // 这里需要替换为实际的Cookie和签名信息
        String cookie = "pluginId=hgjccjcpplholndifgionhehodlehogp; pluginVersion=3.0.5; pluginUUID=10_88751145912-1737440220506-326254; uuid_tt_dd=10_18801881330-1737441779990-712476; fid=20_01261870680-1737441780555-012831; UN=qq_45153900; c_dl_um=-; c_dl_prid=1742449024965_174344; c_dl_rid=1750233372991_566491; c_dl_fref=https://cn.bing.com/; c_dl_fpage=/download/Yao__Shun__Yu/14932325; UserName=qq_45153900; UserInfo=47a711128f5e4c84b93316e3d17ac9e7; UserToken=47a711128f5e4c84b93316e3d17ac9e7; UserNick=%E0%BC%BC%E0%BC%8B%E0%BD%84%E0%BC%8B%E0%BC%BD; AU=177; BT=1758519449071; p_uid=U010000; csdn_newcert_qq_45153900=1; c_ins_fpage=/index.html; c_ins_um=-; ins_first_time=1759130436745; c_ins_prid=1759130436168_857416; c_ins_rid=1759130475298_295999; c_ins_fref=https://blog.csdn.net/amxwsl/article/details/151994372; c_adb=1; c_segment=12; dc_sid=a1260897de0202a06ab0eb1077e00766; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1760321467,1760423993,1761114365; HMACCOUNT=B3D967B08AC19595; c_first_ref=cn.bing.com; _clck=1qe3msd%5E2%5Eg0w%5E0%5E1847; dc_session_id=10_1762830953816.747442; c_first_page=https%3A//www.csdn.net/; c-sidebar-collapse=0; c_ab_test=1; vip_auto_popup=1; c_dsid=11_1762830979164.620454; creative_popup=%7B%22arrowIcon%22%3A%22https%3A//i-operation.csdnimg.cn/images/ce16e44ad48a4f019e83ef26a48123cc.png%22%2C%22img%22%3A%22https%3A//i-operation.csdnimg.cn/images/7010c0819599439aaced9656c020b474.png%22%2C%22imgStyle%22%3A%22height%3A%2088px%3B%22%2C%22darkCfg%22%3A%7B%7D%2C%22role%22%3A%22lost%22%2C%22report%22%3A%7B%22spm%22%3A%223001.11122%22%2C%22extra%22%3A%22%22%7D%2C%22style%22%3A%22%22%2C%22arrowIconStyle%22%3A%22%22%2C%22url%22%3A%22https%3A//mall.csdn.net/vip%3Futm_source%3D251111_vip_blogtopbanner%22%2C%22newTab%22%3Afalse%2C%22userName%22%3A%22qq_45153900%22%7D; is_advert=1; creative_btn_mp=3; c_pref=https%3A//editor.csdn.net/; c_ref=https%3A//mp.csdn.net/; c_page_id=default; log_Id_pv=16; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1762831158; log_Id_view=331; dc_tos=t5jlae; log_Id_click=10";
//        String caKey = "203803574";
//        String caNonce = "59fdfb04-1e1d-490a-9b4b-0354095cdf3b";
//        String caSignature = "FN2aApXbpRfLGV1uKZ2Imi5iqWG5zR86NjUgqwAhKmo=";
//        String caSignatureHeaders = "x-ca-key,x-ca-nonce";

        // 调用接口
        retrofit2.Call<SaveArticleResponse> call = csdnService.saveArticle(cookie, request);

        // 执行请求
        Response<SaveArticleResponse> response = call.execute();

        System.out.println(response.raw());
        System.out.println(response.body());

        // 输出结果
        if (response.isSuccessful() && response.body() != null) {
//            请求成功: SaveArticleResponse(code=200, traceId=1f8d3efe-568f-4f26-b7c3-29279631bf3f, data=SaveArticleResponse.Data(url=https://blog.csdn.net/qq_45153900/article/details/154688223, article_id=154688223, title=测试文章1, description=1111111。), msg=发布成功。)
            System.out.println("请求成功: " + response.body());
        } else {
            System.out.println("请求失败: " + response.code() + " " + response.message());
        }
    }


    @Test
    public void test_md2html() {
        System.out.println(MarkdownConverter.convertToHtml(
                "## \uD83E\uDDE9 1. 核心概念\n" +
                        "\n" +
                        "### ✅ 什么是 MCP？\n" +
                        "\n" +
                        "- **MCP（Model Context Protocol）** 是一种标准化协议，允许 LLM 通过统一接口调用外部工具（如数据库查询、API 调用、文件处理等）。\n" +
                        "- 类似于 OpenAI 的 Function Calling，但更通用、可跨模型/平台。\n" +
                        "- 工具以 **“能力（capabilities）”** 形式注册，LLM 在生成时决定是否调用。" ));
    }

    @Test
    public void test_save_article() throws IOException {
        SaveArticleResponse response = saveArticleMcpServer.saveArticle("测试文章", "<p>1111111</p>\n", "java", "1111111");

        System.out.println(response);
    }
}