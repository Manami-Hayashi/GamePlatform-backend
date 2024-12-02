package be.kdg.prog6.chatbotContext.domain;

import java.util.List;
import java.util.Map;

public class ChatRequest {
    private String userInput;
    private List<Map<String, String>> chatHistory;

    // Constructors, getters, and setters

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public List<Map<String, String>> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(List<Map<String, String>> chatHistory) {
        this.chatHistory = chatHistory;
    }

    @Override
    public String toString() {
        return "ChatRequest{" +
                "userInput='" + userInput + '\'' +
                ", chatHistory=" + chatHistory +
                '}';
    }
}
