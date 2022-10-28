package tasks;

import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class T07AddressesEmployeeCount {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select a from Address as a where a.id not in (296,297)" +
                "order by a.employees.size desc", Address.class).setMaxResults(10);

        List<Address> resultList = query.getResultList();
        StringBuilder stringBuilder = new StringBuilder();
        for (Address a : resultList) {
            stringBuilder.append(String.format("%s %s - %d employees\n",
                    a.getText(),
                    a.getTown().getName(),
                    a.getEmployees().size()));
        }

        System.out.println(stringBuilder);
        entityManager.close();
    }
}
