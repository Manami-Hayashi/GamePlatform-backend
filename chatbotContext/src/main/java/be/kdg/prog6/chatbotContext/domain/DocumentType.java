package be.kdg.prog6.chatbotContext.domain;

public enum DocumentType {
    RULES("rules"),
    USAGE("usage");

    private final String value;

    DocumentType(String value) {
        this.value = value;
    }

    // Static method to get DocumentType from String value
    public static DocumentType fromValue(String value) {
        for (DocumentType type : DocumentType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid DocumentType value: " + value);
    }
}
