package com.cbt.cbtapp.dto;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class LessonResponseDto {

    private String title;
    private Long courseId;
    private Integer indexInsideCourse;
    private String courseTitle;
}
