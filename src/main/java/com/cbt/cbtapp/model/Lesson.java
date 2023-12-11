package com.cbt.cbtapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lesson")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Lesson implements Comparable<Lesson> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer indexInsideCourse;


    public Lesson(String title, Integer indexInsideCourse) {
        this.title = title;
        this.indexInsideCourse = indexInsideCourse;
    }

    public abstract Course getCourse();

    @Override
    public int compareTo(Lesson o) {
        return this.indexInsideCourse.compareTo(o.getIndexInsideCourse());
    }
}
