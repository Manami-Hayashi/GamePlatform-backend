package be.kdg.prog6.storeContext.port.out;

import be.kdg.prog6.storeContext.domain.Transaction;

public interface TransactionCreatedPort {
    void createTransaction(Transaction transaction);
}
