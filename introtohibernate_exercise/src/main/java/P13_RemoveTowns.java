import core.emf;
import entities.Address;
import entities.Employee;
import entities.Town;

import java.util.List;
import java.util.Scanner;

import static common.SqlQueries.*;
import static core.emf.entityManager;

public class P13_RemoveTowns {
    public static void main(String[] args) {

        String townName = new Scanner(System.in).nextLine();

        entityManager.getTransaction().begin();
        try {
            List<Address> addresses = entityManager.createQuery(SELECT_ADDRESS_IN_GIVEN_TOWN, Address.class)
                    .setParameter("town", townName).getResultList();

            for (Address address : addresses) {
                for (Employee employee : address.getEmployees()) {
                    employee.setAddress(null);
                }
                entityManager.remove(address);
            }

            Town town = entityManager.createQuery(SELECT_GIVEN_TOWN, Town.class)
                    .setParameter("town", townName)
                    .getSingleResult();


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
