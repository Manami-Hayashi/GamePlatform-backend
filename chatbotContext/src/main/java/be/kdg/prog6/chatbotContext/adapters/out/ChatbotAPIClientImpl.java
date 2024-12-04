package be.kdg.prog6.chatbotContext.adapters.out;

import be.kdg.prog6.chatbotContext.domain.ChatRequest;
import be.kdg.prog6.chatbotContext.ports.out.ChatbotAPIClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ChatbotAPIClientImpl implements ChatbotAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(ChatbotAPIClientImpl.class);
    private final WebClient webClient;

    public ChatbotAPIClientImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:3000").build();
    }

    @Override
    public String getResponse(ChatRequest chatRequest) {
        try {
            return webClient.post()
                    .uri("/chat")
                    .bodyValue(chatRequest)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            logger.error("Error calling chatbot API", e);
            throw new RuntimeException("Failed to fetch chatbot response", e);
        }
    }
}
