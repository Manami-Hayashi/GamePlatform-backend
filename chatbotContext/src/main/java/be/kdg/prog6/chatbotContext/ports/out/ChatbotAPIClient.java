package be.kdg.prog6.chatbotContext.ports.out;

import be.kdg.prog6.chatbotContext.domain.ChatRequest;

public interface ChatbotAPIClient {
    String getResponse(ChatRequest chatRequest);
}
