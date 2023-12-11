package com.cbt.cbtapp.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class EnrolledCourseDto {

    public Long id;
    public String title;
    public String language;
    public String teacher;
}
