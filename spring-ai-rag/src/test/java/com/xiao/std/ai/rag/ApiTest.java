package com.xiao.std.ai.rag;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: xiaopeng
 * @Description: TODO
 * @DateTime: 2025/11/6 下午4:47 星期四
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApiTest {

    @Test
    public void pdfFile(){

        try {
            // 1. 读取PDF文件
            String filePath = "classpath:documents/pdf/book.pdf";
            PagePdfDocumentReader documentReader = new PagePdfDocumentReader(filePath);

            // 2. 解析PDF内容
            List<Document> documents = documentReader.get();
            System.out.println("📄 解析得到 " + documents.size() + " 个文档块");

            // 3. 验证内容质量
            documents.forEach(doc -> {
                String content = doc.getText();
                if (content == null || content.trim().isEmpty()) {
                    System.out.println("⚠️ 发现空内容文档块");
                }
            });
            // 4. 文本分块处理
            TokenTextSplitter textSplitter = new TokenTextSplitter(
                    1000, // 最大token数
                    200,   // 重叠token数
                    10,    // 最小chunk大小
                    1000,  // 最大chunk大小
                    true
            );
            List<Document> chunks = textSplitter.apply(documents);
            System.out.println("✂️ 分割成 " + chunks.size() + " 个文本块");

            // 5. 验证分块结果
            chunks.forEach(chunk -> {
                System.out.println("📝 分块预览: " +
                        chunk.getText().substring(0, Math.min(50, chunk.getText().length())));
            });

        } catch (Exception e) {
            System.err.println("❌ PDF处理失败: " + e.getMessage());
            e.printStackTrace();
        }


    }
}
