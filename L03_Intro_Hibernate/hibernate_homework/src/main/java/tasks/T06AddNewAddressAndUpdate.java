package tasks;

import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class T06AddNewAddressAndUpdate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter employee last name: ");
        String lastNameEmployee = scanner.nextLine();

        Address newAddress = new Address();
        newAddress.setText("Vitoshka 15");

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.persist(newAddress);

        entityManager.createQuery(
                "update Employee as e set e.address = :newAddressId " +
                        "where e.lastName = :lastName")
                .setParameter("newAddressId", newAddress)
                .setParameter("lastName", lastNameEmployee)
                .executeUpdate();


        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
