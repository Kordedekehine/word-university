package com.cbt.cbtapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "word_to_learn")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WordToLearn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String originalWord;

    @Column(nullable = false)
    private String translation;

    @Column(nullable = false)
    @Size(min = 0,max = 100)
    private Integer collectedPoints;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_enrollment_id", referencedColumnName = "Id")
    private CourseEnrollment courseEnrollment;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "lesson_id", referencedColumnName = "Id")
    private Lesson lesson;

    public WordToLearn(String originalWord, String translation, Integer collectedPoints, CourseEnrollment courseEnrollment, Lesson lesson) {
        this.originalWord = originalWord;
        this.translation = translation;
        this.collectedPoints = collectedPoints;
        this.courseEnrollment = courseEnrollment;
        this.lesson = lesson;
    }
}
