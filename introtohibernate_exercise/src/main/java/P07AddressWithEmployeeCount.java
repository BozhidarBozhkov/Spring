import entities.Address;

import static common.SqlQueries.*;
import static core.emf.entityManager;

public class P07AddressWithEmployeeCount {
    public static void main(String[] args) {

        entityManager.createQuery(FIND_ALL_ADDRESSES_ORDER_BY_NUMBER_OF_EMPLOYEE, Address.class).setMaxResults(10)
                .getResultList().forEach(System.out::println);

        entityManager.close();
    }

}
