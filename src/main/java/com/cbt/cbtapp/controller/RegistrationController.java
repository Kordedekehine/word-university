package com.cbt.cbtapp.controller;


import com.cbt.cbtapp.dto.LoginRequestDto;
import com.cbt.cbtapp.dto.PasswordUpdateDto;
import com.cbt.cbtapp.dto.UserRequestDto;
import com.cbt.cbtapp.exception.authentication.AuthenticationRequiredException;
import com.cbt.cbtapp.exception.authentication.DuplicateUsernameException;
import com.cbt.cbtapp.exception.authentication.IncorrectPasswordException;
import com.cbt.cbtapp.exception.authentication.PasswordMismatchException;
import com.cbt.cbtapp.exception.students.LanguageNotFoundException;
import com.cbt.cbtapp.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class RegistrationController {


     private final LoginService loginService;

     @Autowired
    public RegistrationController(LoginService loginService) {
        this.loginService = loginService;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequestDto userRequestDto) throws DuplicateUsernameException, LanguageNotFoundException {

            return new ResponseEntity<>(loginService.register(userRequestDto), HttpStatus.ACCEPTED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        try {
            return new ResponseEntity<>(loginService.login(loginRequestDto),HttpStatus.ACCEPTED);
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update_password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordUpdateDto passwordUpdateDto) throws PasswordMismatchException, IncorrectPasswordException, AuthenticationRequiredException {

            return new ResponseEntity<>(loginService.updatePassword(passwordUpdateDto),HttpStatus.ACCEPTED);
    }


}
