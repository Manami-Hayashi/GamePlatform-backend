package be.kdg.prog6.chatbotContext.core;

import be.kdg.prog6.chatbotContext.domain.ChatbotGameRule;
import be.kdg.prog6.chatbotContext.domain.Rule;
import be.kdg.prog6.chatbotContext.ports.in.GetGameRulesUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class GetGameRulesUseCaseImpl implements GetGameRulesUseCase {

    private ChatbotGameRule chatbotGameRule;

    public void init() throws IOException {
        loadGameRules();
    }

    // Load the game rules from the Json file
    private void loadGameRules() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("resources/checkers_rules.json");
        this.chatbotGameRule = objectMapper.readValue(jsonFile, ChatbotGameRule.class);

    }

    @Override
    public String getGameRules(String gameName) {
        if (chatbotGameRule == null) {
            return "Sorry, I can't find the game rules at the moment. Please try again later.";
        }

        if ("Checkers".equalsIgnoreCase(gameName)) {
            StringBuilder rules = new StringBuilder();
            rules.append("Game: ").append(chatbotGameRule.getGame()).append("\n");
            for (Rule rule : chatbotGameRule.getRules()) {
                rules.append(rule.getTitle()).append(": ").append(rule.getDescription()).append("\n");
            }
            for (String note: chatbotGameRule.getAdditional_notes()) {
                rules.append(note).append("\n");
            }
            return rules.toString();
        } else {
            return "Sorry, I can't find the game rules for " + gameName + ". Please try again later.";
        }
    }
}
