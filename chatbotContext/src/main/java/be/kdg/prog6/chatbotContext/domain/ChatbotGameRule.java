package be.kdg.prog6.chatbotContext.domain;


import java.util.List;

public class ChatbotGameRule {
    private String game;
    private List<Rule> rules;
    private List<String> additional_notes;

    // Getters and Setters
    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public List<String> getAdditional_notes() {
        return additional_notes;
    }

    public void setAdditional_notes(List<String> additional_notes) {
        this.additional_notes = additional_notes;
    }
}
