package be.kdg.prog6.chatbotContext.ports.in;

@FunctionalInterface
public interface GetResponseUseCase {
    String getChatbotResponse(String userInput);
}
