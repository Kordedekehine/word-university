package com.cbt.cbtapp.controller.studentController;


import com.cbt.cbtapp.dto.SourceLanguageDto;
import com.cbt.cbtapp.dto.TargetLanguageDto;
import com.cbt.cbtapp.dto.WordLearnAnswersDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToStudentsException;
import com.cbt.cbtapp.exception.lessons.LessonNotFoundException;
import com.cbt.cbtapp.exception.lessons.WordToLearnNotFoundException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;
import com.cbt.cbtapp.model.Language;
import com.cbt.cbtapp.service.lessonService.ILessonPracticeService;
import com.cbt.cbtapp.service.translateService.ITranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class TranslationExerciseController {

    @Autowired
    private ILessonPracticeService lessonPracticeService;

    private ITranslationService translationService;

    @GetMapping(value = "/get_word_problem_question")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<?> getWordProblemQuestion(@RequestParam Long lessonId) throws AccessRestrictedToStudentsException, LessonNotFoundException, InvalidCourseAccessException, WordToLearnNotFoundException {

        return new ResponseEntity<>(lessonPracticeService.solveWordQuestion(lessonId),HttpStatus.OK);
    }


    @PostMapping(value = "/answer_word_problem_question")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<?> answerWordProblemQuestion(@RequestBody WordLearnAnswersDto wordLearnAnswersDto) throws AccessRestrictedToStudentsException, InvalidCourseAccessException, WordToLearnNotFoundException {

        return new ResponseEntity<>(lessonPracticeService.answerWordQuestion(wordLearnAnswersDto), HttpStatus.CREATED);
    }

    @PostMapping(value = "/direct_translation")
      public ResponseEntity<?> checkDirectTranslationOfWord(@RequestBody String word,@RequestBody SourceLanguageDto sourceLanguage,@RequestBody TargetLanguageDto targetLanguage){

        return new ResponseEntity<>(translationService.getTranslation(word,sourceLanguage,targetLanguage),HttpStatus.OK);
    }

    @PostMapping(value = "/confirm_direct_translation")
    public ResponseEntity<?> re_checkDirectTranslationOfWord(@RequestBody String word,@RequestBody String translatedWord,@RequestBody SourceLanguageDto sourceLanguage,@RequestBody TargetLanguageDto targetLanguage){

        return new ResponseEntity<>(translationService.isCorrectTranslation(word,translatedWord,sourceLanguage,targetLanguage),HttpStatus.OK);
    }


    @PostMapping(value = "/ai_advanced_translation")
    public ResponseEntity<?> aiAdvancedTranslation(@RequestBody String word, @RequestBody  String sourceLanguage,@RequestBody String targetLanguage){

        return new ResponseEntity<>(translationService.aiAdvancedSearch(word, sourceLanguage, targetLanguage),HttpStatus.OK);
    }
}
