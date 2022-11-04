package entities;

import javax.persistence.*;

@Entity
@Table(name = "prescribed_medicaments")
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private String name;


    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Patient.class, optional = false)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patientId;

    public Medicament() {

    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public Medicament(String name, Patient patient) {
        this.name = name;
        this.patientId = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
