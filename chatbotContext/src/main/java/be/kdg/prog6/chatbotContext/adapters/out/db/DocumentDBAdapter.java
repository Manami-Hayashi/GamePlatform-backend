package be.kdg.prog6.chatbotContext.adapters.out.db;

import be.kdg.prog6.chatbotContext.domain.Document;
import be.kdg.prog6.chatbotContext.domain.DocumentType;
import be.kdg.prog6.chatbotContext.ports.out.DocumentCreatedPort;
import be.kdg.prog6.chatbotContext.ports.out.LoadDocumentPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DocumentDBAdapter implements DocumentCreatedPort, LoadDocumentPort {

    private final DocumentJpaRepository documentJpaRepository;
    private static final Logger logger  = LoggerFactory.getLogger(DocumentDBAdapter.class);

    public DocumentDBAdapter(DocumentJpaRepository documentJpaRepository) {
        this.documentJpaRepository = documentJpaRepository;
    }

    private Document toDocument(DocumentJpaEntity documentEntity) {
        return new Document(documentEntity.getGameName(), documentEntity.getDocumentType(), documentEntity.getContent());
    }


    @Override
    public void createDocument(Document document) {
        DocumentJpaEntity jpaEntity=new DocumentJpaEntity();
        jpaEntity.setGameName(document.getGameName());
        jpaEntity.setDocumentType(document.getDocumentType());
        jpaEntity.setContent(document.getContent());

        logger.info("Saving document with gameName: {} , and documentType: {}", document.getGameName() , document.getDocumentType());
        documentJpaRepository.save(jpaEntity);
    }

    @Override
    public Document findByGameNameAndDocumentType(String gameName, DocumentType documentType) {
        DocumentJpaEntity documentJpaEntity = documentJpaRepository.findByGameNameAndDocumentType(gameName, documentType);
        if (documentJpaEntity != null) {
            logger.info("loading document with gameName: {} , and documentType: {}", gameName , documentType);
            return toDocument(documentJpaEntity);
        }
        logger.error("Document not found with gameName: {} , and documentType: {}", gameName , documentType);
        return null;
    }

    @Override
    public Document findByDocumentType(DocumentType documentType) {
        DocumentJpaEntity documentJpaEntity = documentJpaRepository.findByDocumentType(documentType);
        if (documentJpaEntity != null) {
            logger.info("loading document with documentType: {}", documentType);
            return toDocument(documentJpaEntity);
        }
        logger.error("Document not found with documentType: {}", documentType);
        return null;
    }
}
