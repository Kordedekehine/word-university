package com.cbt.cbtapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "supervised_lesson")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupervisedLesson extends Lesson{

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id", referencedColumnName = "Id")
    private SupervisedCourse course;

    @Override
    public Course getCourse() {
        return course;
    }

    public SupervisedLesson(String title, Integer indexInsideCourse, SupervisedCourse course) {
        super(title, indexInsideCourse);
        this.course = course;
    }

}
