import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Locale;

public class P02_ChangeCasing {
    private static final String DATABASE_NAME = "soft_uni_database";
  //  private static final String UPDATE_ALL_TOWNS_WITH_NAME_LENGTH_MORE_THAN_5 =
   //         "UPDATE Town t SET t.name = UPPER(t.name) WHERE LENGTH(t.name) > 5";

    public static void main(String[] args) {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE_NAME);

        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        List<Town> towns = entityManager.createQuery("SELECT t from Town t", Town.class).getResultList();


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
