package be.kdg.prog6.chatbotContext.core;

import be.kdg.prog6.chatbotContext.ports.in.GetGameRulesUseCase;
import be.kdg.prog6.chatbotContext.ports.in.GetResponseUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetResponseUseCaseImpl implements GetResponseUseCase {

    private final GetGameRulesUseCase getGameRulesUseCase;

    public GetResponseUseCaseImpl(GetGameRulesUseCase getGameRulesUseCase) {
        this.getGameRulesUseCase = getGameRulesUseCase;
    }


    @Override
    public String getChatbotResponse(String userInput) {

        if (userInput.toLowerCase().contains("rules")) {
            // Extract game name from user input (e.g. "rules for Checkers")
            String gameName = userInput.split("rules for")[-1].trim();
            return getGameRulesUseCase.getGameRules(gameName);
        }
        return "";
    }
}
