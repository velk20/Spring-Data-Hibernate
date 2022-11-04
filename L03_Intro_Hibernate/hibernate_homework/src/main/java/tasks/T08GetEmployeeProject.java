package tasks;

import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

public class T08GetEmployeeProject {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Employee id: ");
        int employeeId = Integer.parseInt(scanner.nextLine());

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query id = entityManager.createQuery(
                "select e from Employee  as e where e.id = :ID")
                .setParameter("ID", employeeId);

        List<Employee> resultList = id.getResultList();

        for (Employee employee : resultList) {
            StringBuilder st = new StringBuilder();
            st.append(String.format("%s %s - %s\n",
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getJobTitle()));

            Set<Project> projectSet = employee.getProjects().stream()
                    .sorted(Comparator.comparing(Project::getName))
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            for (Project project : projectSet) {
                st.append(String.format("\t%s\n", project.getName()));
            }

            System.out.println(st.toString().trim());

        }
        entityManager.getTransaction().commit();

        entityManager.close();

    }
}
