import entities.Employee;

import static common.Constants.*;
import static common.SqlQueries.*;
import static core.emf.entityManager;

public class P05_EmployeesFromDepartment {


    public static void main(String[] args) {

        entityManager.createQuery(EXTRACT_ALL_EMPLOYEES_FROM_DEPARTMENT, Employee.class)
                .setParameter("dp", DEPARTMENT).getResultList()
                .forEach(e -> System.out.printf(PRINT_EMP_FORMAT, e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary()));

        entityManager.close();

    }

}
