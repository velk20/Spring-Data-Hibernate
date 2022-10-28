package tasks;

import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class T02ChangeCasing {
    private static final String DATABASE_NAME = "soft_uni";
    private static final String UPDATE_ALL_TOWNS_WITH_LEN_MORE_THAN_5 = "" +
            "update Town as t set t.name= upper(t.name) where LENGTH(t.name) > 5";

    public static void main(String[] args) {
        final EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory(DATABASE_NAME);

        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        Query towns = entityManager
                .createQuery("select t from Town as t",Town.class);

        List<Town> resultList = towns.getResultList();

        for (Town town : resultList) {
            String townName = town.getName();

            if (townName.length() <= 5) {
                town.setName(townName.toUpperCase());
                entityManager.persist(town);
            }
        }
        entityManager.getTransaction().commit();

    }
}
