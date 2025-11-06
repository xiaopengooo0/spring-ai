package com.xiao.std.ai.rag.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentLoader {

    private static final Logger log = LoggerFactory.getLogger(DocumentLoader.class);
    private final VectorStore vectorStore;

    public DocumentLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadDocuments() throws IOException {

        //1.查询向量库,避免调用请求重复向量化
        List<String> vecStores = queryVecStore();
        //2.加载文档
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:documents/*.txt");
        for (Resource resource : resources) {
            log.info("Loading document: {}", resource.getFilename());
            List<Document> documents = new TextReader(resource).get();

            documents.forEach(doc -> {
                String fileName = (String) doc.getMetadata().get("source");
                System.out.println("✅ 文件: " + fileName);
                System.out.println("📝 内容预览: " + doc.getText().substring(0, Math.min(100, doc.getText().length())));
            });

            String source = (String) documents.get(0).getMetadata().get("source");
            if (vecStores.contains(source)){
                log.info("✅ {}已加载，跳过",source);
                continue;
            }
            //3.将文档分割成小块
            var textSplitter = new TokenTextSplitter();
            List<Document> chunks = textSplitter.apply(documents);
            //4.触发嵌入模型对每个文本块进行向量化
            vectorStore.add(chunks);
            log.info("✅ 已加载 {} 个文档块到向量库", chunks.size());
        }


    }

    private List<String> queryVecStore() {
        List<Document> docs = vectorStore.similaritySearch(SearchRequest.builder().query("demo").topK(100).build());
        List<String> fileNames = new ArrayList<>();
        if (!docs.isEmpty()) {
            docs.forEach(doc -> {
                String fileName = (String) doc.getMetadata().get("source");
                fileNames.add(fileName);
                System.out.println("✅ 已查询到文件: " + fileName);
            });
        }
        return fileNames;
    }


}