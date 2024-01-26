package com.cbt.cbtapp.dto;

import com.cbt.cbtapp.model.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class LoginRequestDto {

    private String name;

    private String password;

}
