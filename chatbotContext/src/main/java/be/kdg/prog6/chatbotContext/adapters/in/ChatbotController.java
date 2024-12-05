package be.kdg.prog6.chatbotContext.adapters.in;

import be.kdg.prog6.chatbotContext.domain.ChatRequest;
import be.kdg.prog6.chatbotContext.domain.Message;
import be.kdg.prog6.chatbotContext.ports.in.GetResponseUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chatbot")
public class ChatbotController {
    private static final Logger logger = LoggerFactory.getLogger(ChatbotController.class);
    private final GetResponseUseCase getResponseUseCase;

    public ChatbotController(GetResponseUseCase getResponseUseCase) {
        this.getResponseUseCase = getResponseUseCase;
    }

    @PostMapping("/ask")
    public ResponseEntity<Map<String, String>> handleChat(@RequestBody ChatRequest payload) {
        logger.info("Raw request body: {}", payload);

        // Validate the request payload
        if (payload.getUserInput() == null || payload.getChatHistory() == null) {
            logger.error("Invalid request payload: {}", payload);
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid request payload"));
        }

        try {
            // Extract the chat history as a list of message content
            List<String> chatHistory = payload.getChatHistory().stream()
                    .map(Message::getContent)
                    .collect(Collectors.toList());

            // Pass userInput and chatHistory to the use case
            String botResponse = getResponseUseCase.getChatbotResponse(payload.getUserInput(), chatHistory);

            // Return the bot's response
            return ResponseEntity.ok(Map.of("response", botResponse));
        } catch (Exception e) {
            // Log the error and return a 500 response
            logger.error("Error processing request", e);
            return ResponseEntity.status(500).body(Map.of("error", "Error processing request"));
        }
    }
}
