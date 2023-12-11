package com.cbt.cbtapp.dto;


import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class CourseStatisticsDto {

    int noEnrolledStudents;
    List<String> lessonTitles;
    List<Double> avgNrOfUnknownWordsPerLesson;
}
