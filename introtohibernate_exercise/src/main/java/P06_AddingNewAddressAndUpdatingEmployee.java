import entities.Address;
import java.util.Scanner;

import static common.Constants.*;
import static common.SqlQueries.*;
import static core.emf.entityManager;

public class P06_AddingNewAddressAndUpdatingEmployee {
    public static void main(String[] args) {

        final Scanner scanner = new Scanner(System.in);
        final String lastName = scanner.nextLine();

        entityManager.getTransaction().begin();

        Address newAddress = new Address();
        newAddress.setText(NEW_ADDRESS);

        entityManager.persist(newAddress);

      int count = entityManager.createQuery(SET_NEW_ADDRESS_TO_EMPLOYEE)
                .setParameter("newAddress", newAddress)
                .setParameter("ln", lastName)
                .executeUpdate();

      entityManager.getTransaction().commit();
      entityManager.close();

        System.out.println(count);
    }

}
