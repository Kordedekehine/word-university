package com.cbt.cbtapp.lesson;

import com.cbt.cbtapp.dto.SaveUnknownWordResponse;
import com.cbt.cbtapp.dto.SaveUnknownWordsDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToStudentsException;
import com.cbt.cbtapp.model.*;
import com.cbt.cbtapp.repository.CourseEnrollmentRepository;
import com.cbt.cbtapp.repository.LanguageRepository;
import com.cbt.cbtapp.repository.LessonRepository;
import com.cbt.cbtapp.repository.WordToLearnRepository;
import com.cbt.cbtapp.security.AuthenticationService;
import com.cbt.cbtapp.translate.TranslatorService;
import com.cbt.cbtapp.verifier.RightVerifier;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonStudyService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TranslatorService translatorService;

    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private WordToLearnRepository wordToLearnRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RightVerifier rightVerifier;

    @Autowired
    private VocabularyGenerator vocabularyGenerator;

    @Transactional
    public List<String> getAllLanguages() {
        return languageRepository.findAll().stream().map(Language::getName).collect(Collectors.toList());
    }


    public SaveUnknownWordResponse saveUnknownWord(SaveUnknownWordsDto saveUnknownWordsDto) throws AccessRestrictedToStudentsException {
        Student student = authenticationService.getCurrentStudent();

        Optional<Lesson> lessonOptional = lessonRepository.findById(saveUnknownWordsDto.getLessonId());
        if (lessonOptional.isEmpty()){
            throw new RuntimeException("Lesson ID does not exist!");
        }

        Lesson lesson = lessonOptional.get();

        if (!rightVerifier.hasAccessToTheDataOf(student,lesson)){
            throw new RuntimeException("You have no access to this Data");
        }

        Optional<CourseEnrollment> courseEnrollmentOptional = courseEnrollmentRepository
                .findByCourseAndStudent(lesson.getCourse(),student);


        if (wordToLearnRepository.findByLessonAndCourseEnrollmentAndOriginalWord(lesson,
                courseEnrollmentOptional.get(),saveUnknownWordsDto.getWord()).isPresent()){
            throw new RuntimeException("Word already added! Duplicate words are not allowed");
        }

          String translatedWord = translatorService.getTranslation(saveUnknownWordsDto.getWord(),
                  lesson.getCourse().getLanguage(),student.getNativeLanguage());

        WordToLearn wordToLearn = new WordToLearn(saveUnknownWordsDto.getWord(),translatedWord,0,
                courseEnrollmentOptional.get(),lesson);


        wordToLearnRepository.save(wordToLearn);


    return wordResponse(student.getUserName(),saveUnknownWordsDto.getWord(),lesson.getCourse().getLanguage(),
            translatedWord,student.getNativeLanguage());
    }

    private SaveUnknownWordResponse wordResponse(String username, String unknownWords,Language language,
                                                 String translateWord,Language nativeLanguage){
             return SaveUnknownWordResponse.builder()
                     .word(unknownWords)
                     .username(username)
                     .language(language.toString())
                     .translatedWord(translateWord)
                     .nativeLanguage(nativeLanguage.toString())
                     .build();
    }


public ByteArrayInputStream getLessonVocabularyInPdf(Long lessonId) throws AccessRestrictedToStudentsException {
        Student student = authenticationService.getCurrentStudent();

        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);

        if (lessonOptional.isEmpty()){
            throw new RuntimeException("Lesson does not exist");
        }

        Lesson lesson = lessonOptional.get();

    if (!rightVerifier.hasAccessToTheDataOf(student, lesson)) {
        throw new RuntimeException("You have no access to the data!");
    }

    List<WordToLearn> wordToLearnList = wordToLearnRepository.findByLesson(lesson);

    return vocabularyGenerator.createVocabularyLessonPDF(
            lesson.getTitle(),
            lesson.getCourse().getTitle(),
            lesson.getIndexInsideCourse(),
            wordToLearnList);
}
}


