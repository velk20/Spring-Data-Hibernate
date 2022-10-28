package tasks;

import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class T11FindEmployeesByFirstName {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Employee> resultList = entityManager
                .createQuery("select e from Employee  as e where e.firstName like :input")
                .setParameter("input",input+'%')
                .getResultList();

        for (Employee employee : resultList) {
            System.out.printf("%s %s - %s -($%.2f)\n",
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getJobTitle(),
                    employee.getSalary());
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
