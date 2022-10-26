import entities.Student;

import javax.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Student student = new Student();
        student.setName("Teo");

        entityManager.persist(student);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
