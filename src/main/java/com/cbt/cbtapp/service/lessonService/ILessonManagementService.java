package com.cbt.cbtapp.service.lessonService;

import com.cbt.cbtapp.dto.LessonResponseDto;
import com.cbt.cbtapp.exception.lessons.LessonNotFoundException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;

import java.io.IOException;

public interface ILessonManagementService {

    byte[] getLessonsFile(Long lessonId) throws LessonNotFoundException, InvalidCourseAccessException, IOException;

    LessonResponseDto getLessonData(Long lessonId) throws LessonNotFoundException, InvalidCourseAccessException;
}
