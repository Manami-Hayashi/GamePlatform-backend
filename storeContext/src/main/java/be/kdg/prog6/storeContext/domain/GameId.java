<<<<<<<< HEAD:lobbyManagementContext/src/main/java/be/kdg/prog6/lobbyManagementContext/domain/GameId.java
package be.kdg.prog6.lobbyManagementContext.domain;
========
package be.kdg.prog6.storeContext.domain;
>>>>>>>> fde147d8f7fd4f7d1945e297579a62afc2c5320c:storeContext/src/main/java/be/kdg/prog6/storeContext/domain/GameId.java

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
