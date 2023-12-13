package com.cbt.cbtapp.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class SelfTaughtLessonDto {

    private String title;


    private String language;


    private int minPointsPerWord;
}
