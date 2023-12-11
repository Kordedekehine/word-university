package com.cbt.cbtapp.translate;

//import com.cbt.cbtapp.model.Language;
//import com.google.cloud.translate.Translate;
//import com.google.cloud.translate.testing.RemoteTranslateHelper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Locale;
//
//@Service
//@Slf4j
//public class TranslatorService {
//    @Transactional
//  public String getTranslation(String word, Language sourceLanguage, Language targetLanguage){
//
//      if (sourceLanguage.equals(targetLanguage)) {
//          return word;
//      }
//
//     RemoteTranslateHelper remoteTranslateHelper = RemoteTranslateHelper.create();
//      Translate translate = remoteTranslateHelper.getOptions().getService();
//
//      String translation = translate.translate(word,
//              Translate.TranslateOption.sourceLanguage(sourceLanguage.getAPI_ID()),
//              Translate.TranslateOption.targetLanguage(targetLanguage.getAPI_ID())).getTranslatedText().toString();
//
//      return translation;
//  }
//
//
//    public boolean isCorrectTranslation(String word, String translatedWord, Language sourceLanguage, Language targetLanguage) {
//
//        //COMPARE THE SOURCE AND THE TARGET-TRANSLATED WORDS TO KNOW IF OUR TRANSLATION IS CORRECT
//        boolean correct =
//                getTranslation(word, sourceLanguage, targetLanguage).equals(translatedWord.toLowerCase(Locale.ROOT)) ||
//                        getTranslation(translatedWord, targetLanguage, sourceLanguage).equals(word.toLowerCase(Locale.ROOT));
//
//        if (correct) {
//            log.info("CORRECT - Accepted translation {} in {} to {} in {}", word,
//                    sourceLanguage.getName(),
//                    translatedWord, targetLanguage.getName());
//        } else {
//            log.info("INCORRECT - Declined translation {} in {} to {} in {}", word,
//                    sourceLanguage.getName(),
//                    translatedWord, targetLanguage.getName());
//        }
//
//        return correct;
//    }
//
//}
