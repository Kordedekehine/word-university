package com.cbt.cbtapp.service.translateService;

import com.cbt.cbtapp.dto.SourceLanguageDto;
import com.cbt.cbtapp.dto.TargetLanguageDto;
import com.cbt.cbtapp.model.Language;

public interface ITranslationService {

    String getTranslation(String word, SourceLanguageDto sourceLanguage, TargetLanguageDto targetLanguage);

    //OVERLOADING
    String getTranslation(String word, Language sourceLanguage, Language targetLanguage);

     String aiAdvancedSearch(String word, String sourceLanguage, String targetLanguage);

    boolean isCorrectTranslation(String word, String translatedWord, SourceLanguageDto sourceLanguage, TargetLanguageDto targetLanguage);

    boolean isCorrectTranslation(String word, String translatedWord, Language sourceLanguage, Language targetLanguage);
}
