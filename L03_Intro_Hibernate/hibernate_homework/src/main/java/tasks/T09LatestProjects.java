package tasks;

import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class T09LatestProjects {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("Select p from Project as p order by p.startDate desc").setMaxResults(10);


        List<Project> resultList = query.getResultList();

        for (Project project : resultList) {
            System.out.printf("Project name: %s\n", project.getName());
            System.out.printf("\tProject Description: %s\n",project.getDescription());
            System.out.printf("\tProject Start Date: %s\n",project.getStartDate()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            System.out.printf("\tProject End Date: %s\n",project.getEndDate());
        }
        entityManager.close();
    }
}
