package com.cbt.cbtapp.service.translateService;

import com.cbt.cbtapp.model.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@Slf4j
public class TranslatorService {

    // the actual API key or other authentication methods for production
    private static final String API_KEY = "TRANSLATE_API_KEY"; //SHISHI I NO GET LOL

    private final Translate translate;

    public TranslatorService() {
        this.translate = TranslateOptions.newBuilder().setApiKey(API_KEY).build().getService();
    }

    @Transactional
    public String getTranslation(String word, Language sourceLanguage, Language targetLanguage) {
        if (sourceLanguage.equals(targetLanguage)) {
            return word;
        }

        String translation = translate.translate(word,
                Translate.TranslateOption.sourceLanguage(sourceLanguage.getAPI_ID()),
                Translate.TranslateOption.targetLanguage(targetLanguage.getAPI_ID())).getTranslatedText().toString();

        return translation;
    }

    public boolean isCorrectTranslation(String word, String translatedWord, Language sourceLanguage, Language targetLanguage) {
        // Compare the source and the target-translated words to know if our translation is correct
        boolean correct =
                getTranslation(word, sourceLanguage, targetLanguage).equals(translatedWord.toLowerCase(Locale.ROOT)) ||
                        getTranslation(translatedWord, targetLanguage, sourceLanguage).equals(word.toLowerCase(Locale.ROOT));

        if (correct) {
            log.info("CORRECT - Accepted translation {} in {} to {} in {}", word,
                    sourceLanguage.getName(),
                    translatedWord, targetLanguage.getName());
        } else {
            log.info("INCORRECT - Declined translation {} in {} to {} in {}", word,
                    sourceLanguage.getName(),
                    translatedWord, targetLanguage.getName());
        }

        return correct;
    }
}