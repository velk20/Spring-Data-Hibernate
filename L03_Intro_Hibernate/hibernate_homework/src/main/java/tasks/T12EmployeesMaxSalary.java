package tasks;

import entities.Department;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class T12EmployeesMaxSalary {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();


        List<Department> resultList = entityManager
                .createQuery("select d from Department as d", Department.class)
                .getResultList();

        List<Department> lowerThan = resultList.stream()
                .filter(d -> d.getEmployees().stream()
                        .map(Employee::getSalary)
                        .max(Comparator.naturalOrder())
                        .orElseThrow()
                        .compareTo(new BigDecimal(30000)) < 0
                ).toList();

        List<Department> higherThan = resultList.stream()
                .filter(d ->
                        d.getEmployees().stream()
                                .map(Employee::getSalary)
                                .max(Comparator.naturalOrder())
                                .orElseThrow()
                                .compareTo(new BigDecimal(70000)) > 0
                ).toList();

        List<Department> collect = new ArrayList<>();
        collect.addAll(lowerThan);
        collect.addAll(higherThan);
        collect.sort(Comparator.comparing(Department::getId));

        for (Department department : collect) {
            System.out.printf("%s %.2f\n"
            ,department.getName(),
                    department.getEmployees()
                            .stream()
                            .map(Employee::getSalary)
                            .max(Comparator.naturalOrder())
                            .orElseThrow());
        }

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
