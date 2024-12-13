// chatbotContext/src/main/java/be/kdg/prog6/chatbotContext/core/GetResponseUseCaseImpl.java
package be.kdg.prog6.chatbotContext.core;

import be.kdg.prog6.chatbotContext.domain.Message;
import be.kdg.prog6.chatbotContext.ports.in.GetResponseUseCase;
import be.kdg.prog6.chatbotContext.ports.out.ChatbotPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetResponseUseCaseImpl implements GetResponseUseCase {
    private static final Logger logger = LoggerFactory.getLogger(GetResponseUseCaseImpl.class);
    private final ChatbotPort chatbotPort;

    public GetResponseUseCaseImpl(ChatbotPort chatbotPort) {
        this.chatbotPort = chatbotPort;
    }

    @Override
    public String getChatbotResponse(String userInput, List<String> chatHistory) {
        logger.info("Processing user input: {}", userInput);

        List<Message> messages = chatHistory.stream()
                .map(content -> new Message("user", content))
                .collect(Collectors.toList());

        Message userMessage = new Message("user", userInput);
        messages.add(userMessage);

        String botResponse = chatbotPort.getChatbotResponse(userInput, messages);

        Message botMessage = new Message("assistant", botResponse);
        messages.add(botMessage);

        return botResponse;
    }
}