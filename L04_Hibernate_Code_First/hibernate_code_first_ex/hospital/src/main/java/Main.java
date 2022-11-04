import entities.Diagnose;
import entities.Medicament;
import entities.Patient;
import entities.Visitation;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("relations").createEntityManager();
        entityManager.getTransaction().begin();

        Patient patient = new Patient();
        patient.setFirstName("first");

        entityManager.persist(patient);
        Diagnose diagnose = new Diagnose("rak", "Mnogo zle", entityManager.find(Patient.class, patient.getId()));
        Medicament medicament = new Medicament("Analgin", entityManager.find(Patient.class, patient.getId()));
        Visitation visitation = new Visitation(LocalDate.now(), "Purvo poseshtenie",
                entityManager.find(Patient.class, patient.getId()));

        entityManager.persist(diagnose);
        entityManager.persist(medicament);
        entityManager.persist(visitation);



        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
