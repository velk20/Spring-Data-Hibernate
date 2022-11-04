package tasks;

import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class T03ContainsEmployee {
    private static final String DATABASE_NAME = "soft_uni";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory(DATABASE_NAME);

        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.print("Please enter first and last name of an employee: ");
        String[] names = scanner.nextLine().split("\\s+");

        String firstName = names[0];
        String lastName = names[1];

        entityManager.getTransaction().begin();

        Long res = entityManager.createQuery(
                "Select count(*) from Employee where concat(first_name,' ',last_name)" +
                " = :name",Long.class)
                .setParameter("name", firstName + " " + lastName)
                .getSingleResult();

        if (res == 0) {
            System.out.println("No");
        } else {
            System.out.println("Yes");
        }


        entityManager.close();
    }
}
