// common/src/main/java/be/kdg/prog6/common/domain/PlayerId.java
package be.kdg.prog6.common.domain;

import java.util.UUID;

public class PlayerId {
    private UUID id;

    // Default constructor
    public PlayerId() {
    }

    // Constructor with id parameter
    public PlayerId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}