// chatbotContext/src/main/java/be/kdg/prog6/chatbotContext/ports/out/ChatbotPort.java
package be.kdg.prog6.chatbotContext.ports.out;

import be.kdg.prog6.chatbotContext.domain.Message;

import java.util.List;

@FunctionalInterface
public interface ChatbotPort {
    String getChatbotResponse(String userInput, List<Message> chatHistory);
}