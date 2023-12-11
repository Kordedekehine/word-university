package com.cbt.cbtapp.security;


import com.cbt.cbtapp.model.Student;
import com.cbt.cbtapp.model.Teacher;
import com.cbt.cbtapp.model.User;
import com.cbt.cbtapp.repository.StudentRepository;
import com.cbt.cbtapp.repository.TeacherRepository;
import com.cbt.cbtapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPrincipalService implements UserDetailsService {



    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public UserPrincipalService() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Teacher> optTeacher = teacherRepository.findByUserName(username);
        if (optTeacher.isPresent()) {
            return new AppUser(optTeacher.get());
        }

        Optional<Student> optStudent = studentRepository.findByUserName(username);
        if (optStudent.isPresent()) {
            return new AppUser(optStudent.get());
        }

        // If the user is not found, throw an exception
        throw new UsernameNotFoundException("User Not Found with username: " + username);
    }
}
