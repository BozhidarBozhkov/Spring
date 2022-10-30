import entities.Employee;

import java.util.Scanner;

import static common.SqlQueries.*;
import static core.emf.entityManager;

public class P11_FindEmployeesByFirstName {
    public static void main(String[] args) {

        System.out.print("Enter name starting letters: ");
        String namePattern = new Scanner(System.in).nextLine();

        //Sariya Harnpadoungsataya - Marketing Specialist - ($16128.00)
        entityManager.createQuery(SELECT_EMPLOYEE_BY_NAME_STARTING_WITH_PATTERN, Employee.class)
                .setParameter("p", namePattern + "%")
                .getResultList()
                .forEach(e -> System.out.println(e.getFirstName() + " " + e.getLastName() + " - " +
                        e.getJobTitle() + " - " + "($" + e.getSalary() + ")"));

        entityManager.close();

    }

}
