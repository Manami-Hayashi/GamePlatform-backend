package be.kdg.prog6.chatbotContext.adapters.out;

import be.kdg.prog6.chatbotContext.ports.out.ChatbotPort;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

@Component
public class NlpServiceClient implements ChatbotPort {
    private final String PYTHON_CHATBOT_API_URL = "http://localhost:3000/chat"; // Replace with your Python server's URL if different

    @Override
    public String getChatbotResponse(String userInput) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{\"user_input\": \"%s\"}", userInput);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(PYTHON_CHATBOT_API_URL, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }
}
