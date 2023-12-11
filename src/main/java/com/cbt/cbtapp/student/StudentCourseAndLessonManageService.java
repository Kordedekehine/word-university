package com.cbt.cbtapp.student;


import com.cbt.cbtapp.dto.EnrolledCourseDto;
import com.cbt.cbtapp.dto.ExtendedEnrolledCourseDto;
import com.cbt.cbtapp.dto.SelfTaughtCourseDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToStudentsException;
import com.cbt.cbtapp.exception.lessons.FileStorageException;
import com.cbt.cbtapp.exception.students.CourseNotFoundException;
import com.cbt.cbtapp.exception.students.DuplicateEnrollmentException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;
import com.cbt.cbtapp.exception.students.LanguageNotFoundException;
import com.cbt.cbtapp.lesson.LessonStorageService;
import com.cbt.cbtapp.model.*;
import com.cbt.cbtapp.repository.*;
import com.cbt.cbtapp.security.AuthenticationService;
import com.cbt.cbtapp.verifier.RightVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentCourseAndLessonManageService {


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
    private RightVerifier rightVerifier;

    public SelfTaughtCourse createSelfTaughtCourse(SelfTaughtCourseDto selfTaughtCourseDTO) throws AccessRestrictedToStudentsException, LanguageNotFoundException {
        Student student = authenticationService.getCurrentStudent();

        Optional<Language> optLanguage =
                languageRepository.findByName(selfTaughtCourseDTO.getLanguage());

        if (optLanguage.isEmpty()) {
            throw new LanguageNotFoundException();
        }

        SelfTaughtCourse selfTaughtCourse = new SelfTaughtCourse(selfTaughtCourseDTO.getTitle(),
                selfTaughtCourseDTO.getMinPointsPerWord(),
                optLanguage.get(),
                student);

        selfTaughtCourse.addCourseEnrollment(student);

        SelfTaughtCourse savedCourse = selfTaughtCourseRepository.save(selfTaughtCourse);

        return savedCourse;
    }


    public List<EnrolledCourseDto> getAllEnrolledCourses() throws AccessRestrictedToStudentsException {
        Student student = authenticationService.getCurrentStudent();

        return courseEnrollmentRepository.findByStudent(student)
                .stream()
                .map(CourseEnrollment::getCourse)
                .map(course -> {
                    if (course.getSupervisor().getUserName().equals(student.getUserName())) {
                        return new EnrolledCourseDto(course.getId(), course.getTitle(),
                                course.getLanguage().getName(), null);
                    } else {
                        return new EnrolledCourseDto(course.getId(), course.getTitle(),
                                course.getLanguage().getName(),
                                course.getSupervisor().getUserName());
                    }
                })
                .collect(Collectors.toList());
    }

    public SelfTaughtLesson saveNewSelfTaughtLesson(Long courseId, String title, MultipartFile file) throws AccessRestrictedToStudentsException, CourseNotFoundException, FileStorageException, InvalidCourseAccessException {
        Student student = authenticationService.getCurrentStudent();

        Optional<SelfTaughtCourse> optCourse = selfTaughtCourseRepository.findById(courseId);

        if (optCourse.isEmpty()) {
            throw new CourseNotFoundException();
        }

        SelfTaughtCourse course = optCourse.get();
        if (!rightVerifier.hasRightToModifyTheDataOf(student, course)) {
            throw new InvalidCourseAccessException();
        }

        SelfTaughtLesson selfTaughtLesson = new SelfTaughtLesson(title,
                course.getLessons().size() + 1, course);

        SelfTaughtLesson savedLesson = selfTaughtLessonRepository.save(selfTaughtLesson);


        lessonStorageService.storeLesson(file, savedLesson.getId());

        return savedLesson;
    }

    public ExtendedEnrolledCourseDto getEnrolledCourseData(Long courseId) throws AccessRestrictedToStudentsException, InvalidCourseAccessException, CourseNotFoundException {
        Student student = authenticationService.getCurrentStudent();

        Optional<Course> optCourse = courseRepository.findById(courseId);

        if (optCourse.isEmpty()) {
            throw new CourseNotFoundException();
        }

        Course course = optCourse.get();
        if (!rightVerifier.hasAccessToTheDataOf(student, course)) {
            throw new InvalidCourseAccessException();
        }

        String teacherName = null;
        if (!course.getSupervisor().getUserName().equals(student.getUserName())) {
            teacherName = course.getSupervisor().getUserName();
        }

        return new ExtendedEnrolledCourseDto(course.getId(), course.getTitle(),
                course.getLanguage().getName(), teacherName,
                course.getLessons().stream().collect(Collectors.toMap(Lesson::getId,
                        Lesson::getTitle)));
    }

    public String joinSupervisedCourse(Integer joiningCode) throws CourseNotFoundException,
            AccessRestrictedToStudentsException, DuplicateEnrollmentException {
        Student student = authenticationService.getCurrentStudent();

        Optional<SupervisedCourse> optCourse = supervisedCourseRepository.findByJoiningCode(joiningCode);

        if (optCourse.isEmpty()) {
            throw new CourseNotFoundException();
        }

        SupervisedCourse course = optCourse.get();

        if (course.isEnrolled(student)) {
            throw new DuplicateEnrollmentException();
        }

        course.addCourseEnrollment(student);

        supervisedCourseRepository.save(course);

        return "Student successfully enrolled in course";

    }


}
