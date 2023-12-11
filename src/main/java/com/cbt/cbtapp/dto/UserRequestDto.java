package com.cbt.cbtapp.dto;

import com.cbt.cbtapp.model.*;
import lombok.*;

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


}

