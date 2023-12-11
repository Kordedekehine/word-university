package com.cbt.cbtapp.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class SaveUnknownWordResponse {

private String username;

private String word;

private String language;

private String translatedWord;

private String nativeLanguage;
}
