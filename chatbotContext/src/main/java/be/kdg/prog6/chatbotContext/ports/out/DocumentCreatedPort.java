package be.kdg.prog6.chatbotContext.ports.out;

import be.kdg.prog6.chatbotContext.domain.Document;

public interface DocumentCreatedPort {
    void createDocument(Document document);

}
