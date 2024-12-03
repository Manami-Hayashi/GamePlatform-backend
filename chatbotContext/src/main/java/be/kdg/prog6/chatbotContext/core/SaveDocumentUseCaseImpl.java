package be.kdg.prog6.chatbotContext.core;

import be.kdg.prog6.chatbotContext.domain.Document;
import be.kdg.prog6.chatbotContext.domain.DocumentType;
import be.kdg.prog6.chatbotContext.domain.JsonFileReader;
import be.kdg.prog6.chatbotContext.domain.JsonToDocumentConverter;
import be.kdg.prog6.chatbotContext.ports.in.SaveDocumentUseCase;
import be.kdg.prog6.chatbotContext.ports.out.DocumentCreatedPort;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class SaveDocumentUseCaseImpl implements SaveDocumentUseCase {

    private final DocumentCreatedPort documentCreatedPort;

    public SaveDocumentUseCaseImpl(DocumentCreatedPort documentCreatedPort) {
        this.documentCreatedPort = documentCreatedPort;
    }

    @Override
    public void saveDocument(String gameName, String documentType, String jsonContent) {
        // Step 1: Convert JSON string to Document (GameDocument in this case)
        Document document = convertJsonToDocument(gameName, documentType, jsonContent);

        // Step 2: Save the Document using the DocumentCreatedPort (persistence adapter)
        documentCreatedPort.createDocument(document);
    }

    // Convert JSON content into Document (GameDocument)
    private Document convertJsonToDocument(String gameName, String documentType, String jsonContent) {
        try {

            // Convert string documentType to DocumentType enum
            DocumentType docType = DocumentType.valueOf(documentType.toUpperCase()); // Convert String to DocumentType enum

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode content = objectMapper.readTree(jsonContent); // Convert JSON string to JsonNode

            // Create and return the Document (GameDocument)
            return new Document(gameName, docType, content.toString()); // You could also directly return the JsonNode if preferred
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON to Document", e);
        }
    }
}
