package task_three.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends Person {
    @Column(name = "email")
    private String email;
    @Column(name = "salary_per_hour")
    private Double salaryPerHour;

    public Set<Course> getCourseSet() {
        return courseSet;
    }

    public void addCourseSet(Course course) {
        this.courseSet.add(course);

    }

    @Column(name = "course")
    @OneToMany(mappedBy = "teacher",targetEntity = Course.class)
    private Set<Course> courseSet;

    public Teacher() {
        courseSet = new HashSet<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(Double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }
}
