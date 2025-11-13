package com.xiao.std.ai.mcp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.spec.McpSchema;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class StdioMcpClient {
    private final Process process;
    private final PrintWriter stdin;
    private final BufferedReader stdout;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String clientName = "java-mcp-client";
    private final String clientVersion = "1.0.0";

    public StdioMcpClient(String jarPath) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(
                "java",
                "-Dspring.ai.mcp.server.stdio=true",
                "-jar", jarPath
        );
        pb.redirectErrorStream(true);
        this.process = pb.start();
        this.stdin = new PrintWriter(new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8), true);
        this.stdout = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));

        // 启动一个线程来持续读取服务端输出
//        Thread outputReader = new Thread(this::readAllOutput);
//        outputReader.setDaemon(true);
//        outputReader.start();

        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    /**
     * 持续读取所有服务端输出
     */
    private void readAllOutput() {
        try {
            String line;
            while ((line = stdout.readLine()) != null) {
                System.out.println("服务端输出: " + line);
            }
        } catch (IOException e) {
            System.err.println("读取服务端输出失败: " + e.getMessage());
        }
    }

    /**
     * 发送请求并等待响应
     */
    private JsonNode sendRequest(String method, Object params) {
        String requestId = UUID.randomUUID().toString();
        String request = toJson(Map.of(
                "jsonrpc", "2.0",
                "id", requestId,
                "method", method,
                "params", params
        )) + "\n";

        System.out.println("发送请求: " + request);
        stdin.print(request);
        stdin.flush();

        return waitForResponse(requestId);
    }

    /**
     * 等待特定ID的响应
     */
    private JsonNode waitForResponse(String expectedId) {
        long startTime = System.currentTimeMillis();
        long timeout = 10000;

        // 创建一个临时缓冲区来存储读取的行
        List<String> lines = new ArrayList<>();

        while (System.currentTimeMillis() - startTime < timeout) {
            try {
                // 非阻塞读取
                if (stdout.ready()) {
                    String line = stdout.readLine();
                    if (line != null && !line.trim().isEmpty()) {
                        lines.add(line);
                        System.out.println("收到行: " + line);

                        // 尝试解析为JSON
                        try {
                            JsonNode response = objectMapper.readTree(line);
                            if (response.has("id") && expectedId.equals(response.get("id").asText())) {
                                return response;
                            }
                        } catch (Exception e) {
                            // 不是有效的JSON，继续读取
                        }
                    }
                }

                // 检查进程状态
                if (!process.isAlive()) {
                    System.err.println("服务端进程已退出");
                    return null;
                }

                Thread.sleep(100);
            } catch (Exception e) {
                System.err.println("等待响应时出错: " + e.getMessage());
                break;
            }
        }

        if (!lines.isEmpty()) {
            System.err.println("在超时时间内收到了 " + lines.size() + " 行，但没有匹配的响应ID");
            for (String line : lines) {
                System.err.println("接收到的行: " + line);
            }
        } else {
            System.err.println("读取响应超时，没有收到任何数据");
        }

        return null;
    }

    public boolean initialize() {
        Map<String, Object> initializeParams = Map.of(
                "protocolVersion", "2024-11-05",
                "capabilities", Map.of(
                        "roots", Map.of("listChanged", true),
                        "sampling", Map.of()
                ),
                "clientInfo", Map.of(
                        "name", clientName,
                        "version", clientVersion
                )
        );

        JsonNode response = sendRequest("initialize", initializeParams);
        if (response != null && response.has("result")) {
            // 发送initialized通知
            String notification = toJson(Map.of(
                    "jsonrpc", "2.0",
                    "method", "notifications/initialized",
                    "params", Map.of()
            )) + "\n";
            sendRequest("notifications/initialized", notification);
            System.out.println("发送initialized通知: " + notification);
            stdin.print(notification);
            stdin.flush();

            return true;
        }
        return false;
    }

    public void testVariousMethods() {
        System.out.println("=== 测试各种MCP方法 ===");

        // 测试1: tools/list
        System.out.println("\n1. 测试 tools/list");
        JsonNode toolsResponse = sendRequest("tools/list", Map.of("cursor", ""));
        if (toolsResponse != null) {
            System.out.println("tools/list 响应: " + toolsResponse);
        }

        // 测试2: prompts/list
        System.out.println("\n2. 测试 prompts/list");
        JsonNode promptsResponse = sendRequest("prompts/list", Map.of("cursor", ""));
        if (promptsResponse != null) {
            System.out.println("prompts/list 响应: " + promptsResponse);
        }

        // 测试3: resources/list
        System.out.println("\n3. 测试 resources/list");
        JsonNode resourcesResponse = sendRequest("resources/list", Map.of("cursor", ""));
        if (resourcesResponse != null) {
            System.out.println("resources/list 响应: " + resourcesResponse);
        }

        // 测试4: 简单的ping（如果支持）
        System.out.println("\n4. 测试 ping");
        JsonNode pingResponse = sendRequest("ping", Map.of());
        if (pingResponse != null) {
            System.out.println("ping 响应: " + pingResponse);
        }
    }

    public void checkServerStatus() {
        System.out.println("=== 检查服务端状态 ===");
        System.out.println("进程是否存活: " + process.isAlive());
        try {
            System.out.println("进程退出值: " + process.exitValue());
        } catch (IllegalThreadStateException e) {
            System.out.println("进程仍在运行");
        }
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("JSON序列化失败", e);
        }
    }

    public void close() {
        try {
            if (stdin != null) stdin.close();
            if (stdout != null) stdout.close();
            if (process != null) {
                process.destroy();
                if (!process.waitFor(5, TimeUnit.SECONDS)) {
                    process.destroyForcibly();
                }
            }
        } catch (Exception e) {
            System.err.println("关闭资源失败: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StdioMcpClient client = null;
        try {
            String jarPath = "D:\\public\\maven\\repository\\com\\std\\mcp-server-local\\1.0-SNAPSHOT\\mcp-server-local-1.0-SNAPSHOT.jar";
            client = new StdioMcpClient(jarPath);

            // 等待服务端启动
            Thread.sleep(3000);

            // 检查服务端状态
            client.checkServerStatus();

            // 初始化
            if (!client.initialize()) {
                System.err.println("MCP初始化失败");
                return;
            }

            // 等待一下
            Thread.sleep(2000);

            // 测试各种方法
            client.testVariousMethods();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }
}