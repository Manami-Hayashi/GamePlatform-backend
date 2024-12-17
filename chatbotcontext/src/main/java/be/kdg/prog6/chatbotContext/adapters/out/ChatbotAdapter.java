// chatbotContext/src/main/java/be/kdg/prog6/chatbotContext/adapters/out/ChatbotAdapter.java
package be.kdg.prog6.chatbotContext.adapters.out;

import be.kdg.prog6.chatbotContext.domain.ChatRequest;
import be.kdg.prog6.chatbotContext.domain.ChatResponse;
import be.kdg.prog6.chatbotContext.domain.Message;
import be.kdg.prog6.chatbotContext.ports.out.ChatbotPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Component
public class ChatbotAdapter implements ChatbotPort {
    private static final Logger logger = LoggerFactory.getLogger(ChatbotAdapter.class);
    private final WebClient webClient;

    public ChatbotAdapter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:3000").build(); // Adjust to your Python backend URL
    }

    @Override
    public String getChatbotResponse(String userInput, List<Message> chatHistory) {
        try {
            ChatRequest chatRequest = new ChatRequest(userInput, chatHistory);
            ChatResponse response = webClient.post()
                    .uri("/chat")
                    .bodyValue(chatRequest)
                    .retrieve()
                    .bodyToMono(ChatResponse.class)
                    .block();

            if (response != null) {
                logger.info("Received response from Python backend: {}", response);
                return response.getResponse();
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