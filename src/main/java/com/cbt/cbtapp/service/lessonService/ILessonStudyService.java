package com.cbt.cbtapp.service.lessonService;

import com.cbt.cbtapp.dto.SaveUnknownWordResponse;
import com.cbt.cbtapp.dto.SaveUnknownWordsDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToStudentsException;
import com.cbt.cbtapp.exception.lessons.LessonNotFoundException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ILessonStudyService {

    List<String> getAllLanguages();

    List<String> getAllCourse();

    SaveUnknownWordResponse saveUnknownWord(SaveUnknownWordsDto saveUnknownWordsDto) throws AccessRestrictedToStudentsException, LessonNotFoundException, InvalidCourseAccessException;

    ByteArrayInputStream getLessonVocabularyInPdf(Long lessonId) throws AccessRestrictedToStudentsException, LessonNotFoundException, InvalidCourseAccessException;
}
