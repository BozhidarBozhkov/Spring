package salesDatabase;

import salesDatabase.entities.Customer;
import salesDatabase.entities.Product;
import salesDatabase.entities.Sale;
import salesDatabase.entities.StoreLocation;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("sales_db").createEntityManager();

        entityManager.getTransaction().begin();

        StoreLocation storeLocation = new StoreLocation("MyStore");
        entityManager.persist(storeLocation);

        Customer customer = new Customer("Bozhidar", "bozho@abv.bg", "4400123456789012");
        entityManager.persist(customer);

        Product product = new Product("tyre", 4.0, BigDecimal.valueOf(819.32));
        entityManager.persist(product);

        Sale sale = new Sale(product, customer, storeLocation, LocalDate.now());
        entityManager.persist(sale);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
