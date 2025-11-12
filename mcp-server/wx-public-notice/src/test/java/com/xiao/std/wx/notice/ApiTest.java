package com.xiao.std.wx.notice;

import com.xiao.std.wx.notice.model.WXNoticeFunctionRequest;
import com.xiao.std.wx.notice.service.WxNoticeService;
import jakarta.annotation.Resource;
import jakarta.annotation.security.RunAs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @Author: xiaopeng
 * @Description: TODO
 * @DateTime: 2025/11/12 下午3:19 星期三
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApiTest {

    @Resource
    private WxNoticeService wxNoticeService;


    @Test
    public void test() throws IOException {
        WXNoticeFunctionRequest request = new WXNoticeFunctionRequest();
        request.setPlatform("测试平台");
        request.setSubject("测试主题");
        request.setDescription("测试描述");
        request.setJumpUrl("https://www.baidu.com");
        wxNoticeService.wxNotice(request);
    }
}
