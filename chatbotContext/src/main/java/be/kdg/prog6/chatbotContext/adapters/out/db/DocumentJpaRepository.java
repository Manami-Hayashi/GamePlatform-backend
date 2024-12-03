package be.kdg.prog6.chatbotContext.adapters.out.db;

import be.kdg.prog6.chatbotContext.domain.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentJpaRepository extends JpaRepository<DocumentJpaEntity, Integer> {
    DocumentJpaEntity findByGameNameAndDocumentType(String gameName, DocumentType documentType);
    DocumentJpaEntity findByDocumentType(DocumentType documentType);
}
