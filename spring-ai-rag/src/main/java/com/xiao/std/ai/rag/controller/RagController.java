package com.xiao.std.ai.rag.controller;


import com.xiao.std.ai.rag.service.RagService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rag")
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/ask")
    public String ask(@RequestBody String question) {
        return ragService.ask(question);
    }
}