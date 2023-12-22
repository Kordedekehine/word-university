package com.cbt.cbtapp.service.teacher;

import com.cbt.cbtapp.dto.CourseStatisticsDto;
import com.cbt.cbtapp.dto.LessonStatisticsDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToTeachersException;
import com.cbt.cbtapp.exception.lessons.LessonNotFoundException;
import com.cbt.cbtapp.exception.students.CourseNotFoundException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;
import com.cbt.cbtapp.model.*;
import com.cbt.cbtapp.repository.SupervisedCourseRepository;
import com.cbt.cbtapp.repository.SupervisedLessonRepository;
import com.cbt.cbtapp.repository.WordToLearnRepository;
import com.cbt.cbtapp.security.AuthenticationService;
import com.cbt.cbtapp.verifier.RightVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TeacherStatisticsService implements ITeacherStatisticService{

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SupervisedCourseRepository supervisedCourseRepository;

    @Autowired
    private WordToLearnRepository wordToLearnRepository;

    @Autowired
    private SupervisedLessonRepository supervisedLessonRepository;


    private RightVerifier rightVerifier;

    @Override
    public CourseStatisticsDto getCourseStatistics(Long courseId) throws CourseNotFoundException, InvalidCourseAccessException, AccessRestrictedToTeachersException {
        Teacher teacher = authenticationService.getCurrentTeacher();

        Optional<SupervisedCourse> optCourse = supervisedCourseRepository.findById(courseId);

        if (optCourse.isEmpty()) {
            throw new CourseNotFoundException();
        }

        SupervisedCourse course = optCourse.get();
        if (!rightVerifier.hasAccessToTheDataOf(teacher, course)) {
            throw new InvalidCourseAccessException();
        }

        int nrStudents = course.getEnrollments().size();

        List<String> lessonTitles =
                course.getLessons().stream().map(Lesson::getTitle).collect(Collectors.toList());

        List<Double> avgNrOfUnknownWordsPerLesson = new ArrayList<>();

        if (nrStudents > 0) {
            for (Lesson lesson : course.getLessons()) {
                avgNrOfUnknownWordsPerLesson.add((wordToLearnRepository.findByLesson(lesson).size() / ((double) nrStudents)));
            }
        } else {
            for (Lesson lesson : course.getLessons()) {
                avgNrOfUnknownWordsPerLesson.add(0D);
            }
        }

        return new CourseStatisticsDto(nrStudents, lessonTitles, avgNrOfUnknownWordsPerLesson);
    }

     @Override
    public LessonStatisticsDto getLessonStatistics(Long lessonId) throws LessonNotFoundException, InvalidCourseAccessException, AccessRestrictedToTeachersException {
        Teacher teacher = authenticationService.getCurrentTeacher();

        Optional<SupervisedLesson> optLesson = supervisedLessonRepository.findById(lessonId);
        if (optLesson.isEmpty()) {
            throw new LessonNotFoundException();
        }
        SupervisedLesson lesson = optLesson.get();

        if (!rightVerifier.hasAccessToTheDataOf(teacher, lesson)) {

            throw new InvalidCourseAccessException();
        }

        Map<String, Long> unknownWordToFrequency = wordToLearnRepository.findByLesson(lesson)
                .stream()
                .collect(Collectors.groupingBy(WordToLearn::getOriginalWord,
                        Collectors.counting()));

        return new LessonStatisticsDto(lesson.getTitle(), lesson.getIndexInsideCourse(),
                lesson.getCourse().getTitle(),
                unknownWordToFrequency);
    }
}
