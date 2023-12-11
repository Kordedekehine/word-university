package com.cbt.cbtapp.dto;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class ExtendedEnrolledCourseDto {

    public Long id;
    public String title;
    public String language;
    public String teacher;
    private Map<Long, String> lessonIdsToTitle;
}
