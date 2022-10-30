import entities.Project;

import java.util.Comparator;

import static common.SqlQueries.*;
import static core.emf.entityManager;

public class P09_FindLast10Projects {
    public static void main(String[] args) {

        entityManager.createQuery(FIND_LATEST_10_PROJECTS, Project.class).setMaxResults(10)
                .getResultList().stream().sorted(Comparator.comparing(Project::getName)).forEach(p -> System.out.println(p.toString()));

        entityManager.close();

    }

}
