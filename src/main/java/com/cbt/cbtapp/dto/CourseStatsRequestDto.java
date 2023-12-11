package com.cbt.cbtapp.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class CourseStatsRequestDto {

   private int noEnrolledStudents;
   private List<String> lessonTitles;
   private List<Double> avgNrOfUnknownWordsPerLesson;
}
