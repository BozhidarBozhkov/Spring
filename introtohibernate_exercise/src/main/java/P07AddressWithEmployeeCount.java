import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class P07AddressWithEmployeeCount {
    public static void main(String[] args) {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni_database");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.createQuery("SELECT a FROM Address a " +
                "ORDER BY a.employees.size DESC", Address.class).setMaxResults(10)
                .getResultList().forEach(System.out::println);

        entityManager.close();
    }

}
