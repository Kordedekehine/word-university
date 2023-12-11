package com.cbt.cbtapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_enrollment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "student_id", referencedColumnName = "Id")
    private Student student;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id", referencedColumnName = "Id")
    private Course course;

    public CourseEnrollment(Student student) {
        this.student = student;
    }
}
