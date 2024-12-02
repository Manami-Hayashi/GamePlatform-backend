package be.kdg.prog6.chatbotContext.domain;

public class ChatMessage {
    private String userInput;
    private String response;

    public ChatMessage(String userInput, String response) {
        this.userInput = userInput;
        this.response = response;
    }

    // Getters and setters
    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
