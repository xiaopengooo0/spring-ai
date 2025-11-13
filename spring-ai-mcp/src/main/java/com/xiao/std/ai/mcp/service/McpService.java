package com.xiao.std.ai.mcp.service;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * @Author: xiaopeng
 * @Description: TODO
 * @DateTime: 2025/11/7 上午11:56 星期五
 **/
@Service
public class McpService {

    @Resource
    private ChatClient zhipuAiChatClient;

    public String ask(String question) {
        return zhipuAiChatClient.prompt(question).call().content();
    }

    public String generateArticle(String question) {
        String userInput = """
                我需要你帮我生成一篇文章600字以内，要求如下；
                
                1. 场景为互联网大厂java求职或学习中常遇到的问题
                2. 技术栈如下；
                
                    核心语言与平台: Java SE (8/11/17), Jakarta EE (Java EE), JVM
                    构建工具: Maven, Gradle, Ant
                    Web框架: Spring Boot, Spring MVC, Spring WebFlux, Jakarta EE, Micronaut, Quarkus, Play Framework, Struts (Legacy)
                    数据库与ORM: Hibernate, MyBatis, JPA, Spring Data JDBC, HikariCP, C3P0, Flyway, Liquibase
                    测试框架: JUnit 5, TestNG, Mockito, PowerMock, AssertJ, Selenium, Cucumber
                    微服务与云原生: Spring Cloud, Netflix OSS (Eureka, Zuul), Consul, gRPC, Apache Thrift, Kubernetes Client, OpenFeign, Resilience4j
                    安全框架: Spring Security, Apache Shiro, JWT, OAuth2, Keycloak, Bouncy Castle
                    消息队列: Kafka, RabbitMQ, ActiveMQ, JMS, Apache Pulsar, Redis Pub/Sub
                    缓存技术: Redis, Ehcache, Caffeine, Hazelcast, Memcached, Spring Cache
                    日志框架: Log4j2, Logback, SLF4J, Tinylog
                    监控与运维: Prometheus, Grafana, Micrometer, ELK Stack, New Relic, Jaeger, Zipkin
                    模板引擎: Thymeleaf, FreeMarker, Velocity, JSP/JSTL
                    REST与API工具: Swagger/OpenAPI, Spring HATEOAS, Jersey, RESTEasy, Retrofit
                    序列化: Jackson, Gson, Protobuf, Avro
                    CI/CD工具: Jenkins, GitLab CI, GitHub Actions, Docker, Kubernetes
                    大数据处理: Hadoop, Spark, Flink, Cassandra, Elasticsearch
                    版本控制: Git, SVN
                    工具库: Apache Commons, Guava, Lombok, MapStruct, JSch, POI
                    其他: JUnit Pioneer, Dubbo, R2DBC, WebSocket
                3. 问题的场景方案可包括但不限于；音视频场景,内容社区与UGC,AIGC,游戏与虚拟互动,电商场景,本地生活服务,共享经济,支付与金融服务,互联网医疗,健康管理,医疗供应链,企业协同与SaaS,产业互联网,大数据与AI服务,在线教育,求职招聘,智慧物流,供应链金融,智慧城市,公共服务数字化,物联网应用,Web3.0与区块链,安全与风控,广告与营销,能源与环保。
             
                4. 写到文章最后，讲述出业务场景和技术点，让小白可以学习下来。
                
                根据以上内容，不要阐述其他信息，
                请直接提供：文章标题（需要含带技术点）、文章内容、文章标签（多个用英文逗号隔开）、文章简述（100字）
               
                将以上内容保存文章到CSDN
                """;
        
        return zhipuAiChatClient.prompt(userInput).call().content();
    }

}