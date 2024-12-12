package be.kdg.prog6.storeContext.adapters.out.db;

import be.kdg.prog6.storeContext.domain.Customer;
import be.kdg.prog6.storeContext.port.out.CustomerCreatedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class StoreCustomerDBAdapter implements CustomerCreatedPort {
    private static final Logger logger = LoggerFactory.getLogger(StoreCustomerDBAdapter.class);
    private final StorePlayerRepository storePlayerRepository;

    public StoreCustomerDBAdapter(StorePlayerRepository storePlayerRepository) {
        this.storePlayerRepository = storePlayerRepository;
    }

    @Override
    public void createPlayer(Customer customer) {
        StoreCustomerJpaEntity jpaEntity = new StoreCustomerJpaEntity(
                customer.getCustomerId().id(),
                customer.getName()
        );
        logger.info("creating new Customer with gameName on store db {}", customer.getName());
        // save to database
        storePlayerRepository.save(jpaEntity);
    }
}
