package com.cbt.cbtapp.dto;

import com.cbt.cbtapp.model.Role;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class UserResponseDto {

    private Long id;

    private String name;

    private String password;

    private String emailAddress;

    private Role role;

    private String language;

    private String created;
}
