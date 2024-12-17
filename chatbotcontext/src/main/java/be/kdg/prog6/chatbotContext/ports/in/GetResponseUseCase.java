package be.kdg.prog6.chatbotContext.ports.in;

import java.util.List;

@FunctionalInterface
public interface GetResponseUseCase {
    String getChatbotResponse(String userInput, List<String> chatHistory);
}
