package be.kdg.prog6.chatbotContext.ports.out;

import be.kdg.prog6.chatbotContext.domain.Document;
import be.kdg.prog6.chatbotContext.domain.DocumentType;

public interface LoadDocumentPort {
    Document findByGameNameAndDocumentType(String gameName, DocumentType documentType);
    Document findByDocumentType(DocumentType documentType);
}
