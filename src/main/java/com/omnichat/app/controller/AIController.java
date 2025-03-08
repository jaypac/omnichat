package com.omnichat.app.controller;

import com.omnichat.app.service.OpenAIChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AIController {

    private final OpenAIChatService openAIChatService;

    public AIController(OpenAIChatService openAIChatService) {
        this.openAIChatService = openAIChatService;
    }

    @PostMapping("/chat")
    public Map<String, String> chat(String message) {
        return Collections.singletonMap("message", openAIChatService.chat(message));
    }
}
