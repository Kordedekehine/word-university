package com.cbt.cbtapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    public Student( String userName, String password, Role role, String email, Language language) {
        super( userName, password,role, email, language);
    }

}
