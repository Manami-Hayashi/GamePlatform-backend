package be.kdg.prog6.chatbotContext.domain;

import java.util.List;

public class ChatSession {
    private String sessionId;
    private List<Message> messages;

    public ChatSession() {
    }

    public ChatSession(String sessionId, List<Message> messages) {
        this.sessionId = sessionId;
        this.messages = messages;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
