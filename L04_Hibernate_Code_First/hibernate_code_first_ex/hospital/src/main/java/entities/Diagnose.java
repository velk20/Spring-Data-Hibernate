package entities;

import javax.persistence.*;

@Entity
@Table(name = "diagnoses")
public class Diagnose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "comments")
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Patient.class, optional = false)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patientId;

    public Diagnose() {

    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public Diagnose(String name, String comment, Patient patient) {
        this.name = name;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
