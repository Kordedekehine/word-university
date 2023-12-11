package com.cbt.cbtapp.general;

import com.cbt.cbtapp.dto.CourseResponseDto;
import com.cbt.cbtapp.lesson.LessonStorageService;
import com.cbt.cbtapp.model.Course;
import com.cbt.cbtapp.repository.*;
import com.cbt.cbtapp.security.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class GeneralCheckService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private SelfTaughtCourseRepository selfTaughtCourseRepository;

    @Autowired
    private SelfTaughtLessonRepository selfTaughtLessonRepository;

    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;

    @Autowired
    private SupervisedCourseRepository supervisedCourseRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonStorageService lessonStorageService;

    @Autowired
    private TeacherRepository teacherRepository;


    @Transactional(readOnly = true)
    public List<CourseResponseDto> getAllCourse() {

        List<Course> course = courseRepository.findAll();

        course.sort((c1, c2) -> c2.getTitle().compareTo(c1.getTitle()));
        return course.stream().map(this::mapCourseToResponse).collect(toList());
    }

    private CourseResponseDto mapCourseToResponse(Course course) {
        return CourseResponseDto.builder()
                .title(course.getTitle())
                .language(course.getLanguage().toString())
                .build();
    }


}
