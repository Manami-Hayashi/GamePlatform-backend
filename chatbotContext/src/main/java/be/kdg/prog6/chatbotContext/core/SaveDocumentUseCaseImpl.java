package be.kdg.prog6.chatbotContext.core;

import be.kdg.prog6.chatbotContext.domain.Document;
import be.kdg.prog6.chatbotContext.ports.in.SaveDocumentUseCase;
import be.kdg.prog6.chatbotContext.ports.out.DocumentCreatedPort;
import org.springframework.stereotype.Service;

@Service
public class SaveDocumentUseCaseImpl implements SaveDocumentUseCase {

    private final DocumentCreatedPort documentCreatedPort;

    public SaveDocumentUseCaseImpl(DocumentCreatedPort documentCreatedPort) {
        this.documentCreatedPort = documentCreatedPort;
    }

    @Override
    public void saveDocument(Document document) {
        documentCreatedPort.createDocument(document);
    }
}
