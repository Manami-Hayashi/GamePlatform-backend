package be.kdg.prog6.chatbotContext.ports.in;

import be.kdg.prog6.chatbotContext.domain.Document;

public interface SaveDocumentUseCase {
    void saveDocument(Document document);
}
