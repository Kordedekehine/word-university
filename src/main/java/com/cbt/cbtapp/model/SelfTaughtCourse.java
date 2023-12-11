package com.cbt.cbtapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "self_taught_course")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SelfTaughtCourse extends Course {
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "creator_id", referencedColumnName = "Id")
    private Student creator;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @OrderBy("indexInsideCourse")
    private Set<SelfTaughtLesson> lessons;

    public SelfTaughtCourse(String title, Integer minPointsPerWord, Language language, Student creator) {
        super(title, minPointsPerWord, language);
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "SelfTaughtCourse{" +
                super.toString() +
                "creator=" + creator +
                '}';
    }

    @Override
    public User getSupervisor() {
        return creator;
    }

    @Override
    public List<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }
}
