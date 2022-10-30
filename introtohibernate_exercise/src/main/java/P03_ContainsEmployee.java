import java.util.Scanner;

import static common.SqlQueries.*;
import static core.emf.entityManager;

public class P03_ContainsEmployee {
    public static void main(String[] args) {

        final String[] name = new Scanner(System.in).nextLine().split(" ");

        String firstName = name[0];
        String lastName = name[1];

        final Long countOfMatches = entityManager.createQuery(CHECK_IF_EMPLOYEE_EXISTS_IN_DATABASE, Long.class)
                .setParameter("fn", firstName)
                .setParameter("ln", lastName)
                .getSingleResult();

        if (countOfMatches == 0) {
            System.out.println("No");
        } else {
            System.out.println("Yes");
        }
    }
}
