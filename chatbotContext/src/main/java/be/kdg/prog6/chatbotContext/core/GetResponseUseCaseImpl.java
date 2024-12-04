package be.kdg.prog6.chatbotContext.core;

import be.kdg.prog6.chatbotContext.domain.Message;
import be.kdg.prog6.chatbotContext.ports.in.GetResponseUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetResponseUseCaseImpl implements GetResponseUseCase {
    private static final Logger logger = LoggerFactory.getLogger(GetResponseUseCaseImpl.class);

    @Override
    public String getChatbotResponse(String userInput, List<String> chatHistory) {
        logger.info("Processing user input: {}", userInput);

        // Convert chat history strings to Message objects with timestamps
        List<Message> messages = chatHistory.stream()
                .map(content -> new Message("user", content, LocalDateTime.now())) // Assuming role is "user" for simplicity
                .collect(Collectors.toList());

        // Add the current user input as a new Message
        Message userMessage = new Message("user", userInput, LocalDateTime.now());
        messages.add(userMessage);

        // Example: Log the constructed messages
        logger.info("Constructed chat history with timestamps: {}", messages);

        // Simulate generating a response (Replace with actual logic)
        String botResponse = "This is a simulated response for: " + userInput;

        // Add bot response to the message history (Optional, if you store it)
        Message botMessage = new Message("assistant", botResponse, LocalDateTime.now());
        messages.add(botMessage);

        return botResponse;
    }
}
