package com.cbt.cbtapp.dto;

import com.cbt.cbtapp.model.Role;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto {

   private String userName;

   private String email;
}
