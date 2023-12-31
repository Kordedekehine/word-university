package com.cbt.cbtapp.dto;

import com.cbt.cbtapp.model.*;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class UserRequestDto {

    private String name;

    private String password;

    private String emailAddress;

    private Role role;

    private String language;

    private Instant created;


}

