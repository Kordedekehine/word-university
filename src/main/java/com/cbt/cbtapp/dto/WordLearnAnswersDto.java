package com.cbt.cbtapp.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class WordLearnAnswersDto {

    private Long wordToLearnId;
    private String submittedTranslation;
    private boolean foreignToNative;
}
