package com.cbt.cbtapp.security;


import com.cbt.cbtapp.exception.authentication.AccessRestrictedToStudentsException;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToTeachersException;
import com.cbt.cbtapp.exception.authentication.AuthenticationRequiredException;
import com.cbt.cbtapp.model.Student;
import com.cbt.cbtapp.model.Teacher;
import com.cbt.cbtapp.model.User;
import com.cbt.cbtapp.repository.StudentRepository;
import com.cbt.cbtapp.repository.TeacherRepository;
import com.cbt.cbtapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);


    public User getCurrentUser() throws AuthenticationRequiredException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optUser =
                userRepository.findByUserName(((AppUser) auth.getPrincipal()).getUsername());
        if (optUser.isEmpty()) {
            logger.warn("User tried to access resource without being authenticated");
            throw new AuthenticationRequiredException();
        }
        return optUser.get();
    }


    public Teacher getCurrentTeacher() throws AccessRestrictedToTeachersException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Teacher> optUser =
                teacherRepository.findByUserName(((AppUser) auth.getPrincipal()).getUsername());
        if (optUser.isEmpty()) {
            logger.warn("Non-Teacher User tried to access Teacher-Only resources.");
            throw new AccessRestrictedToTeachersException();
        }
        return optUser.get();
    }


    public Student getCurrentStudent() throws AccessRestrictedToStudentsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Student> optUser =
                studentRepository.findByUserName(((AppUser) auth.getPrincipal()).getUsername());
        if (optUser.isEmpty()) {
            logger.warn("Non-Student User tried to access Student-Only resources.");
            throw new AccessRestrictedToStudentsException();
        }
        return optUser.get();
    }
}
