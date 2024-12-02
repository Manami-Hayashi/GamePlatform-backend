package be.kdg.prog6.chatbotContext.adapters.in;

import be.kdg.prog6.chatbotContext.domain.ChatRequest;
import be.kdg.prog6.chatbotContext.ports.in.GetResponseUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ChatbotController {
    private final GetResponseUseCase getResponseUseCase;

    public ChatbotController(GetResponseUseCase getResponseUseCase) {
        this.getResponseUseCase = getResponseUseCase;
    }

    @PostMapping("/chatbot/ask")
    public ResponseEntity<Map<String, String>> handleChat(@RequestBody ChatRequest payload) {
        System.out.println("Received payload: " + payload);

        try {
            String userInput = payload.getUserInput();
            List<Map<String, String>> chatHistory = payload.getChatHistory();

            if (userInput == null || chatHistory == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid request payload"));
            }

            String botResponse = getResponseUseCase.getChatbotResponse(userInput);
            Map<String, String> response = new HashMap<>();
            response.put("response", botResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error processing request: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", "Error processing request"));
        }
    }
}
