package be.kdg.prog6.chatbotContext.domain;

import be.kdg.prog6.chatbotContext.domain.DocumentType;

public class Document {
    private String gameName;
    private DocumentType documentType; // Now using enum instead of String
    private String content; // The actual JSON content (game rules or platform usage)

    public Document(String gameName, DocumentType documentType, String content) {
        this.gameName = gameName;
        this.documentType = documentType;
        this.content = content;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

