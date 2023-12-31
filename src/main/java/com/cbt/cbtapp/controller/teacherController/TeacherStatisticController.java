package com.cbt.cbtapp.controller.teacherController;

import com.cbt.cbtapp.exception.authentication.AccessRestrictedToTeachersException;
import com.cbt.cbtapp.exception.lessons.LessonNotFoundException;
import com.cbt.cbtapp.exception.students.CourseNotFoundException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;
import com.cbt.cbtapp.service.teacher.ITeacherStatisticService;
import com.cbt.cbtapp.service.teacher.TeacherStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class TeacherStatisticController {

    @Autowired
    private ITeacherStatisticService teacherStatisticsService;

    @GetMapping("/get_course_statistics")
    public ResponseEntity<?> getCourseStatistics(@RequestParam Long courseId) throws CourseNotFoundException, InvalidCourseAccessException, AccessRestrictedToTeachersException {

        return new ResponseEntity<>(teacherStatisticsService.getCourseStatistics(courseId), HttpStatus.OK);
    }

    @GetMapping("/get_lesson_statistics")
    public ResponseEntity<?> getLessonStatistics(@RequestParam Long lessonId) throws LessonNotFoundException, InvalidCourseAccessException, AccessRestrictedToTeachersException {

        return new ResponseEntity<>(teacherStatisticsService.getLessonStatistics(lessonId),HttpStatus.OK);
    }
}
