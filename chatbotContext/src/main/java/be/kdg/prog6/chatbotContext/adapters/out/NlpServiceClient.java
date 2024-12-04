package be.kdg.prog6.chatbotContext.adapters.out;

import be.kdg.prog6.chatbotContext.ports.out.ChatbotPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NlpServiceClient implements ChatbotPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(NlpServiceClient.class);

    @Value("${chatbot.api.url:http://localhost:3000/chat}")
    private String pythonChatbotApiUrl;

    private final RestTemplate chatRestTemplate;

    public NlpServiceClient(RestTemplate chatRestTemplate) {
        this.chatRestTemplate = chatRestTemplate;
    }

    @Override
    public String getChatbotResponse(String userInput) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{\"user_input\": \"%s\"}", userInput);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            LOGGER.info("Sending request to chatbot API: {}", pythonChatbotApiUrl);
            ResponseEntity<String> response = chatRestTemplate.exchange(pythonChatbotApiUrl, HttpMethod.POST, entity, String.class);

            // Validate HTTP response
            if (response.getStatusCode().is2xxSuccessful()) {
                // Parse JSON response
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(response.getBody());
                if (rootNode.has("response")) {
                    String chatbotResponse = rootNode.get("response").asText();
                    LOGGER.info("Chatbot response: {}", chatbotResponse);
                    return chatbotResponse;
                } else {
                    LOGGER.warn("Response does not contain expected field 'response'");
                    return "Unexpected response format from the chatbot.";
                }
            } else {
                LOGGER.error("Chatbot API returned non-success status: {}", response.getStatusCode());
                return "Error contacting the chatbot. Please try again later.";
            }
        } catch (Exception e) {
            LOGGER.error("Error contacting the chatbot", e);
            return "Error contacting the chatbot. Please try again later.";
        }
    }
}
