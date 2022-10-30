import entities.Employee;

import java.util.Scanner;

import static common.SqlQueries.*;
import static core.emf.entityManager;

public class P08_GetEmployeeWithProject {
    public static void main(String[] args) {

        final int empId = new Scanner(System.in).nextInt();

        String employee = entityManager.createQuery(GET_EMPLOYEE_WITH_PROJECT, Employee.class)
                .setParameter("id", empId)
                .getSingleResult().toString();

        System.out.println(employee);
    }

}
