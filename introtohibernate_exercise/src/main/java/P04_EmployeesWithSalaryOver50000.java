import static common.SqlQueries.*;
import static core.emf.entityManager;

public class P04_EmployeesWithSalaryOver50000 {
    public static void main(String[] args) {

        entityManager.getTransaction().begin();

        entityManager.createQuery(SELECT_EMPLOYEE_WITH_SALARY_OVER_50000, String.class)
                .getResultList().forEach(System.out::println);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
