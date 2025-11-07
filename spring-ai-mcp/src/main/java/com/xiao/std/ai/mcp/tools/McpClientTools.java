package com.xiao.std.ai.mcp.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

/**
 * @Author: xiaopeng
 * @Description: MCP客户端本地工具集
 * @DateTime: 2025/11/7 上午11:27 星期五
 **/
@Component
public class McpClientTools {

    @Tool(name = "getComputerInfo",
         description = "获取当前计算机及系统相关信息，包括操作系统、Java版本和内存使用情况")
    public String getComputerInfo() {
        return "计算机名称：" + System.getenv("COMPUTERNAME") + "\n" +
                "操作系统名称：" + System.getProperty("os.name") + "\n" +
                "操作系统版本：" + System.getProperty("os.version") + "\n" +
                "操作系统架构：" + System.getProperty("os.arch") + "\n" +
                "Java版本：" + System.getProperty("java.version") + "\n" +
                "Java运行时环境：" + System.getProperty("java.runtime.name") + "\n"+
                "内存:"+ Runtime.getRuntime().totalMemory()/1024/1024+"M\n"
                +"可用内存:"+Runtime.getRuntime().freeMemory()/1024/1024+"M\n"
                +"最大内存:"+Runtime.getRuntime().maxMemory()/1024/1024+"M\n"
                +"已使用内存:"+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1024/1024+"M";

    }
}