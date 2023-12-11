package com.cbt.cbtapp.dto;

import com.cbt.cbtapp.model.Language;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@ToString
public class CourseResponseDto {

    private String title;

    private Integer minPointsPerWord;


    private String language;

}
