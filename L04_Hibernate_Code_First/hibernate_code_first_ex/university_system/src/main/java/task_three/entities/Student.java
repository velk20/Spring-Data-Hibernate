package task_three.entities;
import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends Person {
    @Column(name = "average_grade")
    private Double averageGrade;

    @Column(name = "attendance")
    private Integer attendance;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "students_courses",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    private Set<Course> courseSet;

    public Set<Course> getCourseSet() {
        return courseSet;
    }

    public void addCourseSet(Course courseSet) {
        this.courseSet.add(courseSet);
    }

    public Student() {
        this.courseSet = new HashSet<>();
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }
}
