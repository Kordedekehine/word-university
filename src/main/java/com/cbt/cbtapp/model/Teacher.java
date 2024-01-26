package com.cbt.cbtapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teacher")
@ToString
@Setter
@Getter
@NoArgsConstructor
public class Teacher extends User{

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Set<SupervisedCourse> taughtCourses;

    public Teacher( String userName, String password,Role role, String email, Language language) {
        super( userName, password, role, email, language);
        this.taughtCourses = new HashSet<>();
    }


}
