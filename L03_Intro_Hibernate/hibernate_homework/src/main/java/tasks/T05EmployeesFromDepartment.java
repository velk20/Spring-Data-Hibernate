package tasks;

import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class T05EmployeesFromDepartment {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery(
                "select e from Employee as e " +
                "order by e.salary " +
                ",e.id ",
                Employee.class);

        List<Employee> resultList = query.getResultList();
        resultList.stream()
                .filter(e->e.getDepartment().getName().equals("Research and Development"))
                .forEach(
                employee->
                        System.out.printf("%s %s from %s - $%.2f\n",
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getDepartment().getName(),
                        employee.getSalary())
        );

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
