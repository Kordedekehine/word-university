package com.cbt.cbtapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "self_taught_lesson")
@NoArgsConstructor
@Getter
@Setter
public class SelfTaughtLesson extends Lesson {

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id", referencedColumnName = "Id")
    private SelfTaughtCourse course;

    @Override
    public Course getCourse() {
        return course;
    }
    public SelfTaughtLesson(String title, Integer indexInsideCourse, SelfTaughtCourse course) {
        super(title, indexInsideCourse);
        this.course = course;
    }
}
