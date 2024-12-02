package be.kdg.prog6.chatbotContext.adapters.in;

import be.kdg.prog6.chatbotContext.ports.in.GetResponseUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ChatbotController {
    private final GetResponseUseCase getResponseUseCase;

    public ChatbotController(GetResponseUseCase getResponseUseCase) {
        this.getResponseUseCase = getResponseUseCase;
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> handleChat(@RequestBody Map<String, Object> payload) {
        String userInput = (String) payload.get("userInput");
        List<Map<String, String>> chatHistory = (List<Map<String, String>>) payload.get("chatHistory");

        String botResponse = getResponseUseCase.getChatbotResponse(userInput);
        Map<String, String> response = new HashMap<>();
        response.put("response", botResponse);
        return ResponseEntity.ok(response);
    }
}
