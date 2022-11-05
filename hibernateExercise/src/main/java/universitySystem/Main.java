package universitySystem;

import universitySystem.entities.Course;
import universitySystem.entities.Student;
import universitySystem.entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        EntityManager entityManager = Persistence.createEntityManagerFactory("university_system").createEntityManager();

        entityManager.getTransaction().begin();

        Student student = new Student("John", "Doe", "08885567410", 4.75, false);
        entityManager.persist(student);

        Teacher teacher = new Teacher("George", "Wrinkle", "01225544", "g.wrinkle@softuni.org", 40.00);
        entityManager.persist(teacher);

        Course course = new Course("MySQL", null, LocalDate.of(2022, 3, 8), LocalDate.of(2023, 9, 12), 9, teacher);
        entityManager.persist(course);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
