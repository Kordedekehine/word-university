package com.cbt.cbtapp.dto;

import com.cbt.cbtapp.model.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class LoginResponseDto {

    private String userName;

    private Role role;

    private String accessToken;

    private String tokenType = "Bearer";


}
