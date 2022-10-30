import entities.Town;

import java.util.List;

import static common.SqlQueries.*;
import static core.emf.entityManager;


public class P02_ChangeCasing {

    public static void main(String[] args) {

        entityManager.getTransaction().begin();

        List<Town> towns = entityManager.createQuery(SELECT_ELIGIBLE_TOWNS, Town.class).getResultList();


        for (Town town : towns) {
            final String townName = town.getName();

            if (townName.length() <= 5) {
                town.setName(townName.toUpperCase());
                entityManager.persist(town);
            }
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
