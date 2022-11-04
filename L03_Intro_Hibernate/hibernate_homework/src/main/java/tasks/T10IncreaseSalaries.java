package tasks;

import entities.Department;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class T10IncreaseSalaries {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        List<String> departments = Arrays.asList(
                "Engineering",
                "Design",
                "Marketing",
                "Information Services",
                "Tool"
        );

        Query de = entityManager.createQuery(
                "select d from Department  as d where d.name in (:de)", Department.class)
                .setParameter("de", departments);
        List<Department> allDepartmentsWithThatName = de.getResultList();

        entityManager
                .createQuery("UPDATE Employee e" +
                        " SET e.salary = e.salary * 1.12" +
                        " WHERE e.department IN (:departmentsS)")
                .setParameter("departmentsS",allDepartmentsWithThatName)
                .executeUpdate();


        Query query = entityManager.createQuery(
                "select e from Employee  as e where e.department in " +
                "(:departmentsS)").setParameter("departmentsS", allDepartmentsWithThatName);

        List<Employee> resultList = query.getResultList();

        for (Employee employee : resultList) {
            System.out.printf("%s %s ($%.2f)\n",
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getSalary());
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
