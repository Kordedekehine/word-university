package com.cbt.cbtapp.service.teacher;

import com.cbt.cbtapp.dto.ExtendedTaughtCourseDto;
import com.cbt.cbtapp.dto.SupervisedCourseDto;
import com.cbt.cbtapp.dto.TaughtCourseDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToTeachersException;
import com.cbt.cbtapp.exception.lessons.FileStorageException;
import com.cbt.cbtapp.exception.students.CourseNotFoundException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;
import com.cbt.cbtapp.exception.students.LanguageNotFoundException;
import com.cbt.cbtapp.model.*;
import com.cbt.cbtapp.repository.LanguageRepository;
import com.cbt.cbtapp.repository.SupervisedCourseRepository;
import com.cbt.cbtapp.repository.SupervisedLessonRepository;
import com.cbt.cbtapp.security.AuthenticationService;
import com.cbt.cbtapp.service.lessonService.LessonStorageService;
import com.cbt.cbtapp.verifier.RightVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TeacherCourseAndLessonManagementService implements ITeacherCourseAndLessonManagementService{

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private SupervisedCourseRepository supervisedCourseRepository;

    @Autowired
    private SupervisedLessonRepository supervisedLessonRepository;

    @Autowired
    private LessonStorageService lessonStorageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RightVerifier rightVerifier;

    @Override
    public ExtendedTaughtCourseDto createSupervisedCourse(SupervisedCourseDto supervisedCourseDto) throws LanguageNotFoundException, AccessRestrictedToTeachersException {

        Teacher teacher = authenticationService.getCurrentTeacher();

        Optional<Language> optLanguage = languageRepository.findByName(supervisedCourseDto.getLanguage());

        if (optLanguage.isEmpty()) {
            throw new LanguageNotFoundException();
        }

        SupervisedCourse supervisedCourse = new SupervisedCourse(supervisedCourseDto.getTitle(),
                supervisedCourseDto.getMinPointsPerWord(),
                optLanguage.get(),
                teacher,
                -1);

        SupervisedCourse savedCourse = supervisedCourseRepository.save(supervisedCourse);

        savedCourse.setJoiningCode(Math.toIntExact(savedCourse.getId()));

        savedCourse = supervisedCourseRepository.save(supervisedCourse);


        return new ExtendedTaughtCourseDto(
                savedCourse.getId(),
                savedCourse.getTitle(),
                savedCourse.getLanguage().getName(),
                savedCourse.getEnrollments().size(),
                savedCourse.getLessons().stream().collect(Collectors.toMap(Lesson::getId,
                        Lesson::getTitle)),
                savedCourse.getJoiningCode()
        );
    }

    @Override
    public List<TaughtCourseDto> getAllTaughtCourses() throws AccessRestrictedToTeachersException {
        Teacher teacher = authenticationService.getCurrentTeacher();

        return teacher.getTaughtCourses()
                .stream()
                .map(course -> new TaughtCourseDto(course.getId(), course.getTitle(),
                        course.getLanguage().getName()))
                .collect(Collectors.toList());
    }


    @Override
    public SupervisedLesson saveNewSupervisedLesson(Long courseId, String title,
                                                    MultipartFile file) throws CourseNotFoundException,
            InvalidCourseAccessException, AccessRestrictedToTeachersException, FileStorageException {
        Teacher teacher = authenticationService.getCurrentTeacher();

        Optional<SupervisedCourse> optCourse = supervisedCourseRepository.findById(courseId);

        if (optCourse.isEmpty()) {
            throw new CourseNotFoundException();
        }

        SupervisedCourse course = optCourse.get();
        if (!rightVerifier.hasRightToModifyTheDataOf(teacher, course)) {
            throw new InvalidCourseAccessException();
        }

        SupervisedLesson supervisedLesson = new SupervisedLesson(title,
                course.getLessons().size() + 1, course);

        SupervisedLesson savedLesson = supervisedLessonRepository.save(supervisedLesson);

        lessonStorageService.storeLesson(file, savedLesson.getId());

        return savedLesson;
    }

    @Override
    public ExtendedTaughtCourseDto getTaughtCourseData(Long courseId) throws InvalidCourseAccessException, CourseNotFoundException, AccessRestrictedToTeachersException {
        Teacher teacher = authenticationService.getCurrentTeacher();

        Optional<SupervisedCourse> optCourse = supervisedCourseRepository.findById(courseId);

        if (optCourse.isEmpty()) {
            throw new CourseNotFoundException();
        }

        SupervisedCourse course = optCourse.get();
        if (!rightVerifier.hasAccessToTheDataOf(teacher, course)) {
            throw new InvalidCourseAccessException();
        }

        return new ExtendedTaughtCourseDto(
                course.getId(),
                course.getTitle(),
                course.getLanguage().getName(),
                course.getEnrollments().size(),
                course.getLessons().stream().collect(Collectors.toMap(Lesson::getId,
                        Lesson::getTitle)),
                course.getJoiningCode());
    }
}
