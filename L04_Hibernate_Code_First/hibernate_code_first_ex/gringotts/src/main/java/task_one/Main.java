package task_one;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("relations");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Gringos gringottsDB = new Gringos("Mladenov",69);

        entityManager.persist(gringottsDB);

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
