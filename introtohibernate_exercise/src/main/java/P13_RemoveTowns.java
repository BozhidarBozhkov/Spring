import entities.Address;
import entities.Employee;
import entities.Town;
import org.dom4j.io.ElementModifier;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class P13_RemoveTowns {
    public static void main(String[] args) {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni_database");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        String townName = new Scanner(System.in).nextLine();

        entityManager.getTransaction().begin();
        try {
            List<Address> addresses = entityManager.createQuery("SELECT a FROM Address a WHERE a.town.name = :town", Address.class)
                    .setParameter("town", townName).getResultList();

            for (Address address : addresses) {
                for (Employee employee : address.getEmployees()) {
                    employee.setAddress(null);
                }
                entityManager.remove(address);
            }

            Town town = entityManager.createQuery("SELECT t FROM Town t WHERE t.name = :town", Town.class)
                    .setParameter("town", townName).getSingleResult();


            if (addresses.size() == 1) {
                System.out.printf("%d address in %s deleted%n", addresses.size(), town.getName());
            } else {
                System.out.printf("%d addresses in %s deleted%n", addresses.size(), town.getName());
            }
            entityManager.remove(town);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }

        entityManager.close();
    }

}
