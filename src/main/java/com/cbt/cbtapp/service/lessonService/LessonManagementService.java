package com.cbt.cbtapp.service.lessonService;

import com.cbt.cbtapp.dto.LessonResponseDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToStudentsException;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToTeachersException;
import com.cbt.cbtapp.exception.lessons.LessonNotFoundException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;
import com.cbt.cbtapp.model.Lesson;
import com.cbt.cbtapp.model.User;
import com.cbt.cbtapp.repository.LessonRepository;
import com.cbt.cbtapp.security.AuthenticationService;
import com.cbt.cbtapp.verifier.RightVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class LessonManagementService {

    private LessonStorageService lessonStorageService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LessonRepository lessonRepository;


    @Autowired
    private RightVerifier rightVerifier;


    @Transactional
    public byte[] getLessonsFile(Long lessonId) throws IOException, AccessRestrictedToStudentsException, LessonNotFoundException, InvalidCourseAccessException {
        User user;

        try {
            user = authenticationService.getCurrentStudent();
        } catch (AccessRestrictedToStudentsException e) {
            try {
                user = authenticationService.getCurrentTeacher();
            } catch (AccessRestrictedToTeachersException e1) {
                throw new AccessRestrictedToStudentsException();
            }
        }

        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (lesson.isEmpty()) {
            throw new LessonNotFoundException();
        }

        if (!rightVerifier.hasAccessToTheDataOf(user, lesson.get())) {
            throw new InvalidCourseAccessException();
        }

        return lessonStorageService.getLessonFile(lessonId);
    }


    public LessonResponseDto getLessonData(Long lessonId) throws LessonNotFoundException, InvalidCourseAccessException {
        User user;

        try {
            user = authenticationService.getCurrentStudent();
        } catch (AccessRestrictedToStudentsException e) {
            try {
                user = authenticationService.getCurrentTeacher();
            } catch ( AccessRestrictedToTeachersException exception) {
                throw new IllegalStateException("Unknown user type");
            }
        }

        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (lesson.isEmpty()) {
            throw new LessonNotFoundException();
        }

        if (!rightVerifier.hasAccessToTheDataOf(user, lesson.get())) {
            throw new InvalidCourseAccessException();
        }

        return new LessonResponseDto(
                lesson.get().getTitle(),
                lesson.get().getCourse().getId(),
                lesson.get().getIndexInsideCourse(),
                lesson.get().getCourse().getTitle());
    }


}
