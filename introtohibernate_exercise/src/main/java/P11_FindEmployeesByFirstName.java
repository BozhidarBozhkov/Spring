import entities.Employee;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class P11_FindEmployeesByFirstName {
    public static void main(String[] args) {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni_database");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.print("Enter name starting letters: ");
        String namePattern = new Scanner(System.in).nextLine();

        //Sariya Harnpadoungsataya - Marketing Specialist - ($16128.00)
        entityManager.createQuery("SELECT e FROM Employee e WHERE e.firstName LIKE :p", Employee.class)
                .setParameter("p", namePattern + "%")
                .getResultList()
                .forEach(e -> System.out.println(e.getFirstName() + " " + e.getLastName() + " - " +
                        e.getJobTitle() + " - " + "($" + e.getSalary() + ")"));

        entityManager.close();

    }

}
