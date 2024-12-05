package be.kdg.prog6.chatbotContext.ports.out;

@FunctionalInterface
public interface ChatbotPort {
    String getChatbotResponse(String userInput);
}
