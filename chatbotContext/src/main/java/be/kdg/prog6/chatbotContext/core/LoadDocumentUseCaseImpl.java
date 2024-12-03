package be.kdg.prog6.chatbotContext.core;

import be.kdg.prog6.chatbotContext.domain.Document;
import be.kdg.prog6.chatbotContext.ports.in.LoadDocumentUseCase;
import be.kdg.prog6.chatbotContext.ports.out.LoadDocumentPort;
import org.springframework.stereotype.Service;

@Service
public class LoadDocumentUseCaseImpl implements LoadDocumentUseCase {

    private final LoadDocumentPort loadDocumentPort;

    public LoadDocumentUseCaseImpl(LoadDocumentPort loadDocumentPort) {
        this.loadDocumentPort = loadDocumentPort;
    }

    @Override
    public Document getGameRules(String gameName) {
        return null;
    }

    @Override
    public Document getPlatformUsage() {
        return null;
    }
}
