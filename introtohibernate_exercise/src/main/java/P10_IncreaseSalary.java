import entities.Employee;

import static common.SqlQueries.*;
import static core.emf.entityManager;


public class P10_IncreaseSalary {
    public static void main(String[] args) {

        entityManager.getTransaction().begin();

        entityManager.createQuery(UPDATE_EMPLOYEE_SALARY).executeUpdate();

        entityManager.getTransaction().commit();

        entityManager.createQuery(SELECT_UPDATED_EMPLOYEE_SALARY, Employee.class)
                .getResultList()
                .forEach(e -> System.out.printf("%s %s ($%.2f)%n", e.getFirstName(), e.getLastName(), e.getSalary()));

        entityManager.close();

    }

}
