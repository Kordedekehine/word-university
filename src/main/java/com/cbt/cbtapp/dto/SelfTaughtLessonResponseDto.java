package com.cbt.cbtapp.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class SelfTaughtLessonResponseDto {

    private Long courseId;

    private String title;

    private MultipartFile file;
}
