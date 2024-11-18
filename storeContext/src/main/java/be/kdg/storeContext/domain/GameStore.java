package be.kdg.storeContext.domain;

import java.util.List;
import java.util.UUID;

public class GameStore {
    private UUID storeId;
    private List<StoreGame> gameCatalog;
    private List<Transaction> transactionList;

    public GameStore(UUID storeId, List<StoreGame> gameCatalog, List<Transaction> transactionList) {
        this.storeId = storeId;
        this.gameCatalog = gameCatalog;
        this.transactionList = transactionList;
    }

    public UUID getStoreId() {
        return storeId;
    }

    public List<StoreGame> getGameCatalog() {
        return gameCatalog;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }
}
