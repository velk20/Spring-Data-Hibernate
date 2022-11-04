package entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "visitations")
public class Visitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "comments")
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Patient.class, optional = false)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patientId;

    public Visitation(LocalDate date, String comment,Patient patient) {
        this.patientId = patient;
        this.date = date;
        this.comment = comment;
    }

    public Visitation() {

    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
