package be.kdg.prog6.chatbotContext.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ChatRequest {
    private String userInput;
    private List<Message> chatHistory;

    // Constructors, getters, and setters

    public ChatRequest() {
    }

    public ChatRequest(String userInput) {
        this.userInput = userInput;
    }

    public ChatRequest(String userInput, List<Message> chatHistory) {
        this.userInput = userInput;
        this.chatHistory = chatHistory;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public List<Message> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(List<Message> chatHistory) {
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
