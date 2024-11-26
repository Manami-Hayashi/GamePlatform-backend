<<<<<<<< HEAD:storeContext/src/main/java/be/kdg/prog6/storeContext/domain/GameId.java
package be.kdg.prog6.storeContext.domain;
========
package be.kdg.prog6.lobbyManagementContext.domain;
>>>>>>>> addGame:lobbyManagementContext/src/main/java/be/kdg/prog6/lobbyManagementContext/domain/GameId.java

import java.util.UUID;

public record GameId(UUID id) {

    public GameId {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
    }

    public GameId(String id) {
        this(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
