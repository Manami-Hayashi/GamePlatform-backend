package be.kdg.prog6.chatbotContext.adapters.out.db;


import be.kdg.prog6.chatbotContext.domain.DocumentType;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;

@Entity
@Table(catalog = "chatbot", name = "documents")
public class DocumentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "game_name")
    private String gameName;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Lob // used to store large text fields
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    public DocumentJpaEntity(String gameName, DocumentType documentType, String content) {
        this.gameName = gameName;
        this.documentType = documentType;
        // Serialize the JSON content to a String before storing it
        this.content = content;
    }

    public DocumentJpaEntity() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
