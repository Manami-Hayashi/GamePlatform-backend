package be.kdg.prog6.chatbotContext.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToDocumentConverter {

    public static Document convertJsonToDocument(String jsonContent, String gameName, DocumentType documentType) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode content = null;

        try {
            content = objectMapper.readTree(jsonContent); // deserialize JSON content to JsonNode

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Document(gameName, documentType, content != null ? content.toString() : null); // Assuming content is a string
    }
}
