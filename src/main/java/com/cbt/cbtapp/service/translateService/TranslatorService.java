package com.cbt.cbtapp.service.translateService;

import com.cbt.cbtapp.dto.SourceLanguageDto;
import com.cbt.cbtapp.dto.TargetLanguageDto;
import com.cbt.cbtapp.model.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Map;

@Service
@Slf4j
public class TranslatorService implements ITranslationService{

    /**
     * THE GOOGLE TRANSLATION IS THERE TO HELP US DIRECTLY TRANSLATE THE SOURCE WORD TO THE TARGET WORD
     * IT IS PRETTY MUCH STRAIGHT FORWARD LIKE spanish: gracias to english- thank you
     *
     * THAT'S WHY WE ALSO HAVE THE ADVANCED SEARCH WITH OPEN AI KNOWN AS CHAT GPT.
     * THIS IS TO GIVE US THE IN-DEPTH ANALYSIS OF BOTH WORDS
     */

    private static final String GOOGLE_API_KEY = "TRANSLATE_API_KEY"; //SHISHI I NO GET LOL

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    private final Translate translate;

    private final AiClient aiClient;

    public TranslatorService(AiClient aiClient) {
        this.aiClient = aiClient;
        this.translate = TranslateOptions.newBuilder().setApiKey(GOOGLE_API_KEY).build().getService();
    }

    @Transactional
    @Override
    public String getTranslation(String word, SourceLanguageDto sourceLanguage, TargetLanguageDto targetLanguage) {
        if (sourceLanguage.equals(targetLanguage)) {
            return word;
        }

        String translation = translate.translate(word,
          Translate.TranslateOption.sourceLanguage(sourceLanguage.getLanguage_Api_id()),
          Translate.TranslateOption.targetLanguage(targetLanguage.getLanguage_Api_id())).getTranslatedText().toString();

        return translation;
    }

    @Transactional
    @Override
    public String getTranslation(String word, Language sourceLanguage, Language targetLanguage) {
        if (sourceLanguage.equals(targetLanguage)) {
            return word;
        }

        String translation = translate.translate(word,
                Translate.TranslateOption.sourceLanguage(sourceLanguage.getAPI_ID()),
                Translate.TranslateOption.targetLanguage(targetLanguage.getAPI_ID())).getTranslatedText().toString();

        return translation;
    }

    @Override
    public boolean isCorrectTranslation(String word, String translatedWord, SourceLanguageDto sourceLanguage, TargetLanguageDto targetLanguage) {
        // Compare the source and the target-translated words to know if our translation is correct
        boolean correct =
                getTranslation(word, sourceLanguage, targetLanguage).equals(translatedWord.toLowerCase(Locale.ROOT)) ||
                        getTranslation(translatedWord, sourceLanguage,targetLanguage).equals(word.toLowerCase(Locale.ROOT));

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

    @Override
    public boolean isCorrectTranslation(String word, String translatedWord, Language sourceLanguage, Language targetLanguage) {
        // Compare the source and the target-translated words to know if our translation is correct
        boolean correct =
                getTranslation(word, sourceLanguage, targetLanguage).equals(translatedWord.toLowerCase(Locale.ROOT)) ||
                        getTranslation(translatedWord, sourceLanguage,targetLanguage).equals(word.toLowerCase(Locale.ROOT));

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

    @Override
    public String aiAdvancedSearch(String word, String sourceLanguage, String targetLanguage) {
        PromptTemplate promptTemplate = new PromptTemplate(
        " Examine the word " + word + ". delve into its meaning in " + sourceLanguage + " language" +
        "and explore its meaning in " + targetLanguage + " language.  Keep the analysis brief and " +
        "light-hearted, focusing on synonyms and cultural connotations within each language.");
        Map.of("word", word, "sourceLanguage", sourceLanguage,"targetLanguage",targetLanguage)
                .forEach(promptTemplate::add);
        AiResponse generate = this.aiClient.generate(promptTemplate.create());
        return generate.getGeneration().getText();
    }
}