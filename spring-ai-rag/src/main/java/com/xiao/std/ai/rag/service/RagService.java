package com.xiao.std.ai.rag.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RagService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public RagService(VectorStore vectorStore, ChatClient.Builder chatClientBuilder) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder.build();
    }

    public String ask(String question) {

        List<Document> docs = vectorStore.similaritySearch(SearchRequest.builder().query(question).topK(3).build());

        // 👇 打印检索结果（关键！）
        log.info("检索到 {} 个文档", docs.size());
        for (int i = 0; i < docs.size(); i++) {
            log.info("文档 {}: {}", i, docs.get(i).getMetadata().get("source"));
        }

        String context = docs.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n---\n"));

        String prompt = """
                你是一个公司内部助手，请严格基于以下上下文回答问题。
                如果上下文没有相关信息，请回答：“我不知道”。

                上下文：
                %s

                问题：%s
                """.formatted(context, question);

        return chatClient.prompt(prompt).call().content();
    }
}