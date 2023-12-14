package com.cbt.cbtapp.service.userService;



import com.cbt.cbtapp.dto.LoginRequestDto;
import com.cbt.cbtapp.dto.LoginResponseDto;
import com.cbt.cbtapp.dto.PasswordUpdateDto;
import com.cbt.cbtapp.dto.UserRequestDto;
import com.cbt.cbtapp.exception.authentication.AuthenticationRequiredException;
import com.cbt.cbtapp.exception.authentication.DuplicateUsernameException;
import com.cbt.cbtapp.exception.authentication.IncorrectPasswordException;
import com.cbt.cbtapp.exception.authentication.PasswordMismatchException;
import com.cbt.cbtapp.exception.students.LanguageNotFoundException;
import com.cbt.cbtapp.model.Student;
import com.cbt.cbtapp.model.Teacher;
import com.cbt.cbtapp.model.User;
import com.cbt.cbtapp.repository.LanguageRepository;
import com.cbt.cbtapp.repository.StudentRepository;
import com.cbt.cbtapp.repository.TeacherRepository;
import com.cbt.cbtapp.repository.UserRepository;
import com.cbt.cbtapp.security.AppUser;
import com.cbt.cbtapp.security.AuthenticationService;
import com.cbt.cbtapp.security.JwtUtils;
import com.cbt.cbtapp.security.UserFactory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;


    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;


    @Transactional
    public UserRequestDto register(UserRequestDto userRequestDto) throws DuplicateUsernameException, LanguageNotFoundException {

        Optional<User> userName = userRepository.findByUserName(userRequestDto.getName());
        if (userName.isPresent()) {
            throw new DuplicateUsernameException();
        }


        User user = UserFactory.buildUser(userRequestDto, passwordEncoder, languageRepository);

        if (user instanceof Student) {
            studentRepository.save((Student) user);
        } else if (user instanceof Teacher) {
            teacherRepository.save((Teacher) user);
        }

        modelMapper.map(user, userRequestDto);

        userRepository.save(user);

        modelMapper.map(user,userRequestDto);

        return userRequestDto;
    }


    public String updatePassword(PasswordUpdateDto passwordUpdateDto) throws IncorrectPasswordException, PasswordMismatchException, AuthenticationRequiredException {
        User user = authenticationService.getCurrentUser();

        if (!passwordEncoder.matches(passwordUpdateDto.getOldPassword(), user.getPassword())) {
            throw new IncorrectPasswordException();
        }

        if (!passwordUpdateDto.getNewPassword().equals(passwordUpdateDto.getConfirmNewPassword())) {
            throw new PasswordMismatchException();
        }

        user.setPassword(passwordEncoder.encode(passwordUpdateDto.getNewPassword()));
        userRepository.save(user);

        return "Password Updated!";
    }

//    public LoginResponseDto login(LoginRequestDto loginRequestDto){
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequestDto.getName(),loginRequestDto.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = jwtUtils.generateJwtToken(authentication);
//
//         AppUser user = (AppUser) authentication.getPrincipal();// = (AppUser) authentication.getPrincipal();
//
//        log.info(jwt.toString());
//
//        String tokenType = "";
//
//        return mapUserToLogin(loginRequestDto,user,jwt,tokenType);
//
//    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getName(), loginRequestDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (authentication == null || !authentication.isAuthenticated()) {
                throw new RuntimeException("Authentication failed.");
            }

            Object principal = authentication.getPrincipal();

            if (principal instanceof AppUser) {
                AppUser user = (AppUser) principal;
                log.info("Login successful for user: {}", user.getUsername());

                String jwt = jwtUtils.generateJwtToken(authentication);

                return mapUserToLogin(loginRequestDto, user, jwt);
            } else if (principal instanceof String) {
                log.warn("Principal is a String: {}", principal);
                throw new RuntimeException("Unexpected principal type: " + principal);
            } else {
                throw new RuntimeException("Unknown principal type: " + principal);
            }
        } catch (Exception e) {
            log.error("Login failed: {}", e.getMessage());
            throw e;
        }
    }

    private LoginResponseDto mapUserToLogin(LoginRequestDto loginRequestDto, AppUser user, String jwt) {
        return LoginResponseDto.builder()
                .userName(loginRequestDto.getName())
                .role(user.getRole()) // Use the getRole method
                .accessToken(jwt)
                .tokenType("Bearer ")
                .build();
    }

}
