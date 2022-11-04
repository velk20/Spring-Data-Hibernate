package tasks;

import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class T13RemoveTowns {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String town = scanner.nextLine();

        EntityManager entityManager = Persistence.createEntityManagerFactory("soft_uni").createEntityManager();
        entityManager.getTransaction().begin();

        List<Address> addresses = entityManager
                .createQuery("select a from Address as a where a.town.name = :town", Address.class)
                .setParameter("town", town)
                .getResultList();


        for (Address address : addresses) {
            for (Employee employee : address.getEmployees()) {
                employee.setAddress(null);
            }
            entityManager.remove(address);

        }


        entityManager.getTransaction().commit();
        entityManager.close();
        System.out.printf("%d %s in %s deleted",
                addresses.size(),
                addresses.size() > 1 ? "addresses" : "address",
                town);
    }
}
