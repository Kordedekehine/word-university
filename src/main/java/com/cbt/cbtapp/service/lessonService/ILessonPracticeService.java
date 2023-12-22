package com.cbt.cbtapp.service.lessonService;

import com.cbt.cbtapp.dto.WordLearnAnswersDto;
import com.cbt.cbtapp.dto.WordLearningPracticeDto;
import com.cbt.cbtapp.dto.WordLearningPracticeVerifyDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToStudentsException;
import com.cbt.cbtapp.exception.lessons.LessonNotFoundException;
import com.cbt.cbtapp.exception.lessons.WordToLearnNotFoundException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;

public interface ILessonPracticeService {

    WordLearningPracticeDto solveWordQuestion(Long lessonId) throws AccessRestrictedToStudentsException, LessonNotFoundException, InvalidCourseAccessException, WordToLearnNotFoundException;

    WordLearningPracticeVerifyDto answerWordQuestion(WordLearnAnswersDto wordLearnAnswersDto) throws AccessRestrictedToStudentsException, WordToLearnNotFoundException, InvalidCourseAccessException;
}
