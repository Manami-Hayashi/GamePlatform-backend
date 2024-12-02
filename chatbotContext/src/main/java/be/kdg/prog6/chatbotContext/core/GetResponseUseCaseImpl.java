package be.kdg.prog6.chatbotContext.core;

import be.kdg.prog6.chatbotContext.ports.in.GetResponseUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetResponseUseCaseImpl implements GetResponseUseCase {

    @Override
    public String getChatbotResponse(String userInput) {
        return "";
    }
}
