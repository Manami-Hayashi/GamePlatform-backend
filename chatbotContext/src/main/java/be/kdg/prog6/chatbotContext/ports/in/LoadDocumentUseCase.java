package be.kdg.prog6.chatbotContext.ports.in;

import be.kdg.prog6.chatbotContext.domain.Document;

public interface LoadDocumentUseCase {
    Document getGameRules(String gameName);
    Document getPlatformUsage();
}
