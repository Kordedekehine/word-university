package com.cbt.cbtapp.service.teacher;

import com.cbt.cbtapp.dto.CourseStatisticsDto;
import com.cbt.cbtapp.dto.LessonStatisticsDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToTeachersException;
import com.cbt.cbtapp.exception.lessons.LessonNotFoundException;
import com.cbt.cbtapp.exception.students.CourseNotFoundException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;

public interface ITeacherStatisticService {

    CourseStatisticsDto getCourseStatistics(Long courseId) throws CourseNotFoundException, InvalidCourseAccessException, AccessRestrictedToTeachersException;

    LessonStatisticsDto getLessonStatistics(Long lessonId) throws LessonNotFoundException, InvalidCourseAccessException, AccessRestrictedToTeachersException;
}
