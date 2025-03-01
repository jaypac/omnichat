package com.omnichat.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OpenAIChatService {

    private final Logger LOG = LoggerFactory.getLogger(OpenAIChatService.class);

    private final ChatClient chatClient;

    public OpenAIChatService(@Qualifier("openAIChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chat(String message) {

        var content = chatClient.prompt()
                .user(message)
                .call()
                .content();

        LOG.debug("Open AI response: {}", content);
        return content;
    }
}
