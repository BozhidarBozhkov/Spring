import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Comparator;

public class P09_FindLast10Projects {
    public static void main(String[] args) {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni_database");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.createQuery("SELECT p FROM Project p ORDER BY p.startDate desc", Project.class).setMaxResults(10)
                .getResultList().stream().sorted(Comparator.comparing(Project::getName)).forEach(p -> System.out.println(p.toString()));

        entityManager.close();

    }

}
