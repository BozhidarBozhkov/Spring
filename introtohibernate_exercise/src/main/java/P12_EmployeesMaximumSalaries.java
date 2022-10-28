import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Comparator;

public class P12_EmployeesMaximumSalaries {
    public static void main(String[] args) {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni_database");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();


        entityManager.createQuery("SELECT e FROM Employee e GROUP BY e.department HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000", Employee.class)
                .getResultList().forEach(e -> System.out.println(e.getDepartment().getName() + " " + e.getSalary()));


        entityManager.close();
    }

}
