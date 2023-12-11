package com.cbt.cbtapp.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class PasswordUpdateDto {

    private String oldPassword;

    private String newPassword;

    private String confirmNewPassword;
}
