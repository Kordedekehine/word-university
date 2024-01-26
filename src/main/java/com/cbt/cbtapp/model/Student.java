package com.cbt.cbtapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "student")
@ToString
@Setter
@Getter
@NoArgsConstructor
public class Student extends User {

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<CourseEnrollment> enrollments;

    @ManyToOne
    @JoinColumn(name = "score_id", referencedColumnName = "Id")
    private Score score;

    public Student( String userName, String password, Role role, String email, Language language) {
        super( userName, password, role, email, language);
    }


    public int getScorePoints() {
        return (score != null) ? score.getPoints() : 0;
    }

}
