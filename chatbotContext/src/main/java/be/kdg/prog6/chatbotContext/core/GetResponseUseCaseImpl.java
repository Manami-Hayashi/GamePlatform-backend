package be.kdg.prog6.chatbotContext.core;

import be.kdg.prog6.chatbotContext.domain.ChatRequest;
import be.kdg.prog6.chatbotContext.domain.ChatResponse;
import be.kdg.prog6.chatbotContext.domain.Message;
import be.kdg.prog6.chatbotContext.ports.in.GetResponseUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetResponseUseCaseImpl implements GetResponseUseCase {
    private static final Logger logger = LoggerFactory.getLogger(GetResponseUseCaseImpl.class);

    private final WebClient webClient;

    public GetResponseUseCaseImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:3000").build(); // Adjust to your Python backend URL
    }

    @Override
    public String getChatbotResponse(String userInput, List<String> chatHistory) {
        logger.info("Processing user input: {}", userInput);

        // Convert chat history strings to Message objects with timestamps
        List<Message> messages = chatHistory.stream()
                .map(content -> new Message("user", content))
                .collect(Collectors.toList());

        // Add the current user input as a new Message
        Message userMessage = new Message("user", userInput);
        messages.add(userMessage);

        // Call Python FastAPI backend
        String botResponse = callPythonBackend(userInput, messages);

        // Add bot response to the message history (Optional, if you store it)
        Message botMessage = new Message("assistant", botResponse);
        messages.add(botMessage);

        return botResponse;
    }

    private String callPythonBackend(String userInput, List<Message> chatHistory) {
        try {
            // Prepare request payload
            ChatRequest chatRequest = new ChatRequest(userInput, chatHistory);

            // Call Python backend using WebClient
            ChatResponse response = webClient.post()
                    .uri("/chat")
                    .bodyValue(chatRequest)
                    .retrieve()
                    .bodyToMono(ChatResponse.class)
                    .block();

            if (response != null) {
                logger.info("Received response from Python backend: {}", response);
                return response.getResponse();  // Assuming 'response' has a 'getResponse' method
            } else {
                logger.error("Empty response from Python backend.");
                return "Unable to get a response from the chatbot.";
            }
        } catch (WebClientResponseException e) {
            logger.error("Error communicating with Python backend: {}", e.getResponseBodyAsString(), e);
            return "An error occurred while connecting to the chatbot. Please check the backend.";
        } catch (Exception e) {
            logger.error("Unexpected error during communication with Python backend", e);
            return "An unexpected error occurred while connecting to the chatbot.";
        }
    }
}
