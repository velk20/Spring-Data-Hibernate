package tasks;

import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class T04EmployeesWithSalary {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("Select e from Employee as e where  e.salary > 50000");

        List<Employee> resultList = query.getResultList();
        for (Employee employee : resultList) {
            System.out.println(employee.getFirstName());
        }
        entityManager.close();
    }
}
