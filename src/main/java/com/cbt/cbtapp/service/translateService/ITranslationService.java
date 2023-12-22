package com.cbt.cbtapp.service.translateService;

import com.cbt.cbtapp.model.Language;

public interface ITranslationService {

    String getTranslation(String word, Language sourceLanguage, Language targetLanguage);

    boolean isCorrectTranslation(String word, String translatedWord, Language sourceLanguage, Language targetLanguage);
}
