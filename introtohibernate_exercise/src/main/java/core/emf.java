package core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class emf {

    private static final String DATABASE_NAME = "soft_uni_database";
    final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE_NAME);
    public static final EntityManager entityManager = entityManagerFactory.createEntityManager();

}
