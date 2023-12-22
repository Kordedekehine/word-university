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

public interface ILoginService {

    UserRequestDto register(UserRequestDto userRequestDto) throws DuplicateUsernameException, LanguageNotFoundException;

    String updatePassword(PasswordUpdateDto passwordUpdateDto) throws IncorrectPasswordException, PasswordMismatchException, AuthenticationRequiredException;

     LoginResponseDto login(LoginRequestDto loginRequestDto);

     Long getAllUsers();
}
