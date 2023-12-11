package com.cbt.cbtapp.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class SaveUnknownWordsDto {

    private Long lessonId;

    private String word;
}
