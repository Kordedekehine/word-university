package com.cbt.cbtapp.security;

import com.cbt.cbtapp.model.Role;
import com.cbt.cbtapp.model.Student;
import com.cbt.cbtapp.model.Teacher;
import com.cbt.cbtapp.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class AppUser extends User implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final User user;
    private final Collection<GrantedAuthority> authorities;


    public Role getRole() {
        return getCreateRole();
    }


    public AppUser(Teacher admin) {
        this.user = admin;
        this.authorities = List.of(new SimpleGrantedAuthority(Authorities.TEACHER.toString()));
    }

    public AppUser(Student student) {
        this.user = student;
        this.authorities = List.of(new SimpleGrantedAuthority(Authorities.STUDENT.toString()));
    }

    public enum Authorities {
        TEACHER,
        STUDENT
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
       AppUser user = (AppUser) o;
        return Objects.equals(getId(), user.user.getId());
    }
}
