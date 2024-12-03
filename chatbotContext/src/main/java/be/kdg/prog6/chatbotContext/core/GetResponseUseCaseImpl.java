package be.kdg.prog6.chatbotContext.core;

import be.kdg.prog6.chatbotContext.ports.in.GetResponseUseCase;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GetResponseUseCaseImpl implements GetResponseUseCase {

    private static final String PYTHON_CHATBOT_API_URL = "http://localhost:3000/chat";

    @Override
    public String getChatbotResponse(String userInput) {
        try {
            // Prepare headers and body for the request
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String requestBody = String.format("{\"user_input\": \"%s\"}", userInput);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            // Send the request to Python chatbot
            ResponseEntity<String> response = restTemplate.exchange(PYTHON_CHATBOT_API_URL, HttpMethod.POST, entity, String.class);

            // Return the response from the chatbot
            return response.getBody();
        } catch (Exception e) {
            // Handle error
            return "Error contacting the chatbot. Please try again later.";
        }
    }
}
