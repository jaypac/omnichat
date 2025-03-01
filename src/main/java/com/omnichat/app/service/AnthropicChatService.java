package com.omnichat.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AnthropicChatService {

    private final Logger LOG = LoggerFactory.getLogger(AnthropicChatService.class);

    private final ChatClient chatClient;

    public AnthropicChatService(@Qualifier("anthropicChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chat(String message) {

        var content = chatClient.prompt()
                .user(message)
                .call()
                .content();

        LOG.debug("Anthropic response: {}", content);
        return content;
    }
}
