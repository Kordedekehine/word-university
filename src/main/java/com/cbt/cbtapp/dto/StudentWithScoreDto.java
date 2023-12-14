package com.cbt.cbtapp.dto;

import com.cbt.cbtapp.model.Student;
import lombok.*;


@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentWithScoreDto {

    private Student student;
    private Integer highestScore;
}
