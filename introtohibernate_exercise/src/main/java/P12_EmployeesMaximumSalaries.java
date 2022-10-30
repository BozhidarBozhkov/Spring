import entities.Employee;

import static common.SqlQueries.*;
import static core.emf.entityManager;

public class P12_EmployeesMaximumSalaries {
    public static void main(String[] args) {

        entityManager.createQuery(SELECT_MAX_SALARY_FOR_DEPARTMENT, Employee.class)
                .getResultList()
                .forEach(e -> System.out.println(e.getDepartment().getName() + " " + e.getSalary()));

        entityManager.close();
    }

}
