package com.cbt.cbtapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Inheritance(strategy = InheritanceType.JOINED) //because we are dealing with abstract class
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "create_role", nullable = false)
    private Role createRole;

    @Column(nullable = false)
    private String email;


    @OneToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language nativeLanguage;

    private Instant created;

    public User(String userName, String password,Role createRole, String email, Language nativeLanguage) {
        this.userName = userName;
        this.password = password;
        this.createRole = createRole;
        this.email = email;
        this.nativeLanguage = nativeLanguage;
    }



    public String toString(){
        return "User{password='" + getPassword() + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (Id != null ? !Id.equals(user.Id) : user.Id != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null)
            return false;
        if (password != null ? !password.equals(user.password) : user.password != null)
            return false;
        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        int result = Id != null ? Id.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

}
