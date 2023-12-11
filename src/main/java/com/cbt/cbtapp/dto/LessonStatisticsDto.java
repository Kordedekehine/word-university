package com.cbt.cbtapp.dto;

import lombok.*;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class LessonStatisticsDto {

    String title;
    Integer indexInsideCourse;
    String courseTitle;
    Map<String, Long> unknownWordsToFrequency;
}
