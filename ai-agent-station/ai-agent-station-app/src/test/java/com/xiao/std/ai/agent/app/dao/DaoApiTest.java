package com.xiao.std.ai.agent.app.dao;

import com.xiao.std.ai.agent.infrastructure.dao.IAiAgentDao;
import com.xiao.std.ai.agent.infrastructure.po.AiAgent;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: xiaopeng
 * @Description: TODO
 * @DateTime: 2025/11/14 下午4:33 星期五
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class DaoApiTest {
    @Resource
    private IAiAgentDao aiAgentDao;


    @Test
    public void query_all(){
        List<AiAgent> list = aiAgentDao.queryAll();
        System.out.println(list);
    }
}
