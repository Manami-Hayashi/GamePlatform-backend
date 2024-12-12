package be.kdg.prog6.storeContext.adapters.out.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(catalog = "store", name = "customer")
public class StoreCustomerJpaEntity {
    @Id
    @Column(name="customer_id")
    private UUID customerId;

    @Column(name="gameName")
    private String name;

    public StoreCustomerJpaEntity() {
    }

    public StoreCustomerJpaEntity(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

}
