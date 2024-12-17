package be.kdg.prog6.storeContext.port.out;


import be.kdg.prog6.storeContext.domain.Customer;

@FunctionalInterface
public interface CustomerCreatedPort {
    void createPlayer(Customer customer);
}
