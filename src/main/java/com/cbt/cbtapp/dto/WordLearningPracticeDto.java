package com.cbt.cbtapp.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class WordLearningPracticeDto {

    private Long wordToLearnId;
    private Long lessonId;
    private String word;
    private int currentPoints;
    private int targetPoints;
    private String sourceLanguage;
    private String targetLanguage;
    private boolean foreignToNative;

}
