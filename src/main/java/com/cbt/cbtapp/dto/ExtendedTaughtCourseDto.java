package com.cbt.cbtapp.dto;


import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class ExtendedTaughtCourseDto {

    public Long id;
    public String title;
    public String language;
    public int nrOfStudents;
    private Map<Long, String> lessonIdsToTitle;
    public int joiningCode;

}
