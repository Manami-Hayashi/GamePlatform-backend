package be.kdg.prog6.storeContext.adapters.out.db;

import be.kdg.prog6.storeContext.domain.Transaction;
import be.kdg.prog6.storeContext.port.out.TransactionCreatedPort;
import org.springframework.stereotype.Component;

@Component
public class TransactionCreatedPortImpl implements TransactionCreatedPort {
    private final TransactionRepository transactionRepository;

    public TransactionCreatedPortImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void createTransaction(Transaction transaction) {
        TransactionJpaEntity transactionJpaEntity = new TransactionJpaEntity(
                transaction.getTransactionId(),
                transaction.getGameId().id(),
                transaction.getCustomerId().id(),
                transaction.getPrice(),
                transaction.getCreatedAt()
        );
        transactionRepository.save(transactionJpaEntity);
    }
}