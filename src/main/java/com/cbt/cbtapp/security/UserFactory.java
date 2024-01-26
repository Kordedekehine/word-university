package com.cbt.cbtapp.security;


import com.cbt.cbtapp.dto.UserRequestDto;
import com.cbt.cbtapp.exception.students.LanguageNotFoundException;
import com.cbt.cbtapp.model.Role;
import com.cbt.cbtapp.model.User;
import com.cbt.cbtapp.repository.LanguageRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

public class UserFactory {

    public static User buildUser(UserRequestDto userRequestDto, PasswordEncoder passwordEncoder,
                                 LanguageRepository languageRepository) throws LanguageNotFoundException {
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        User user = userRequestDto.getRole().buildUser(userRequestDto, languageRepository
                .findByName(userRequestDto.getLanguage()).orElseThrow(LanguageNotFoundException::new));
        user.setCreateRole(userRequestDto.getRole());
        user.setCreated(Instant.now());
        return user;
    }
}
