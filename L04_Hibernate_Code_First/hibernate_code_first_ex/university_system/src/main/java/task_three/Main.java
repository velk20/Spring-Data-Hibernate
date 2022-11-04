package task_three;


import task_three.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("relations").createEntityManager();

        entityManager.getTransaction().begin();

        Course course = new Course();
        Student student = new Student();
        Teacher teacher = new Teacher();
        entityManager.persist(teacher);

        course.setTeacher(entityManager.find(Teacher.class, teacher.getId()));
        entityManager.persist(course);
        entityManager.persist(student);

        teacher.addCourseSet(course);

        student.addCourseSet(course);

        course.addStudentSet(student);

        entityManager.persist(course);
        entityManager.persist(student);
        entityManager.persist(teacher);

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
