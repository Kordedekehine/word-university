package com.cbt.cbtapp.lesson;


import com.cbt.cbtapp.dto.WordLearnAnswersDto;
import com.cbt.cbtapp.dto.WordLearningPracticeDto;
import com.cbt.cbtapp.dto.WordLearningPracticeVerifyDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToStudentsException;
import com.cbt.cbtapp.exception.authentication.AuthenticationRequiredException;
import com.cbt.cbtapp.model.*;
import com.cbt.cbtapp.repository.CourseEnrollmentRepository;
import com.cbt.cbtapp.repository.LessonRepository;
import com.cbt.cbtapp.repository.WordToLearnRepository;
import com.cbt.cbtapp.security.AuthenticationService;
import com.cbt.cbtapp.translate.TranslatorService;
import com.cbt.cbtapp.verifier.RightVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class LessonPracticeService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TranslatorService translatorService;

    @Autowired
    private WordToLearnRepository wordToLearnRepository;

    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private RightVerifier rightVerifier;

    @Autowired
    private VocabularyGenerator vocabularyGenerator;

    @Autowired
    private ScoreUpdater scoreUpdater;


    public WordLearningPracticeDto solveQuestion(Long lessonId) throws AccessRestrictedToStudentsException {

        Student student = authenticationService.getCurrentStudent();

        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);

        if (lessonOptional.isEmpty()){
            throw new RuntimeException("Lesson ID does not exist!");
        }

        Lesson lesson = lessonOptional.get();

        Course course = lesson.getCourse();

        if (!rightVerifier.hasAccessToTheDataOf(student,lesson)){
            throw new RuntimeException("You have no access to this Data");
        }

        //check if student enrolled
        Optional<CourseEnrollment> courseEnrollment =
                courseEnrollmentRepository.findByCourseAndStudent(lesson.getCourse(), student);

        List<WordToLearn> wordsToLearn = wordToLearnRepository.findByLessonAndCourseEnrollmentAndCollectedPoints(
                lesson, courseEnrollment.get(), course.getMinPointsPerWord());


        if (wordsToLearn.isEmpty()) {
            throw new RuntimeException("No unknown words as for now!");
        }

        WordToLearn wordToLearn = wordsToLearn.get(new Random().nextInt(wordsToLearn.size()));

        if (randomTranslate()){
            return new WordLearningPracticeDto(wordToLearn.getId(), lessonId, wordToLearn.getOriginalWord(),
                    wordToLearn.getCollectedPoints(), course.getMinPointsPerWord(),
                    wordToLearn.getLesson().getCourse().getLanguage().getName(),
                    student.getNativeLanguage().getName(), true);
        }else {
            return new WordLearningPracticeDto(wordToLearn.getId(), lessonId, wordToLearn.getTranslation(),
                    wordToLearn.getCollectedPoints(), course.getMinPointsPerWord(),
                    student.getNativeLanguage().getName(),
                    wordToLearn.getLesson().getCourse().getLanguage().getName(), false);
        }
    }

    private boolean randomTranslate() {
        return new Random().nextBoolean();
    }

    public WordLearningPracticeVerifyDto practiceVerifyDto(WordLearnAnswersDto wordLearnAnswersDto) throws AccessRestrictedToStudentsException {
        Student student = authenticationService.getCurrentStudent();

        Optional<WordToLearn> wordToLearnOptional = wordToLearnRepository.findById(wordLearnAnswersDto.getWordToLearnId());

        if (wordToLearnOptional.isEmpty()) {
            throw new RuntimeException("Word does not exist!");
        }

        if (!rightVerifier.hasAccessToTheDataOf(student, wordToLearnOptional.get().getLesson())) {
            throw new RuntimeException("You have no access to the data");
        }

        WordToLearn wordToLearn = wordToLearnOptional.get();
        //NOW VERIFY THE

        //if foreignToNative is true then assign the source language the wordToLearn (word to be learned)
        //if foreign language is false then the source language is assigned the student native language
        //regular logic with tenary
        Language sourceLanguage = wordLearnAnswersDto.isForeignToNative() ?
                wordToLearn.getLesson().getCourse().getLanguage() :
                student.getNativeLanguage();

        //if foreignToNative is true then assign the target language to the student native language
        //if foreignToNative is false assign it to the wordToLearn language
        Language targetLanguage = wordLearnAnswersDto.isForeignToNative() ?
                student.getNativeLanguage() :
                wordToLearn.getLesson().getCourse().getLanguage();

        //Select based on if foreign native is true or false
        String word = wordLearnAnswersDto.isForeignToNative() ? wordToLearn.getOriginalWord() :
                wordToLearn.getTranslation();
        String officialTranslation = wordLearnAnswersDto.isForeignToNative() ? wordToLearn.getTranslation() :
                wordToLearn.getOriginalWord();

        boolean isCorrect = translatorService.isCorrectTranslation(word, wordLearnAnswersDto.getSubmittedTranslation(),
                sourceLanguage, targetLanguage);

        wordToLearn.setCollectedPoints(scoreUpdater.getNewPoints(wordToLearn.getCollectedPoints(),isCorrect));

        return new WordLearningPracticeVerifyDto(
                wordLearnAnswersDto.getWordToLearnId(),
                wordToLearn.getLesson().getId(),
                word,
                wordToLearn.getCollectedPoints(),
                wordToLearn.getLesson().getCourse().getMinPointsPerWord(),
                officialTranslation,
                wordLearnAnswersDto.getSubmittedTranslation(),
                isCorrect,
                sourceLanguage.getName(),
                targetLanguage.getName(),
                wordLearnAnswersDto.isForeignToNative());

    }


}
