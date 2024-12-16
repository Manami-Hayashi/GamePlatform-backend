package be.kdg.prog6.storeContext.domain;

public class Customer {
    private CustomerId customerId;
    private String name;

    public Customer(CustomerId customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}