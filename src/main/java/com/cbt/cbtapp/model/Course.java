package com.cbt.cbtapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer minPointsPerWord;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "language_id", referencedColumnName = "Id")
    private Language language;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<CourseEnrollment> enrollments;

    public Course(String title, Integer minPointsPerWord, Language language) {
        this.title = title;
        this.minPointsPerWord = minPointsPerWord;
        this.language = language;
        this.enrollments = new HashSet<>();
    }


    public void addCourseEnrollment(Student student) {
        CourseEnrollment enrollment = new CourseEnrollment(student);
        this.enrollments.add(enrollment);
        enrollment.setCourse(this);
    }
    public abstract User getSupervisor();

    public abstract List<Lesson> getLessons();

    public boolean isEnrolled(Student student) {
        return enrollments.stream().anyMatch(courseEnrollment -> courseEnrollment.getStudent().equals(student));
    }

}
