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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Slf4j
public class LessonManagementService implements ILessonManagementService{

    @Autowired
    private LessonStorageService lessonStorageService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LessonRepository lessonRepository;


    @Autowired
    private RightVerifier rightVerifier;


    @Override
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


    @Override
    public byte[] getLessonsFile(Long lessonId) throws LessonNotFoundException, InvalidCourseAccessException, IOException {

                User user;
        try {
            user = authenticationService.getCurrentStudent();
        } catch (AccessRestrictedToStudentsException e) {
            try {
                user = authenticationService.getCurrentTeacher();
            } catch (AccessRestrictedToTeachersException accessRestrictedToTeachersException) {
                throw new IllegalStateException("Unknown user type");
            }
        }

        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (lesson.isEmpty()) {
            log.warn("INVALID REQUEST - file of lesson {] could not be retrieved, because " +
                    "lesson does not exist.");
            throw new LessonNotFoundException();
        }

        if (!rightVerifier.hasAccessToTheDataOf(user, lesson.get())) {
            log.warn("INVALID ACCESS - user {} attempted to access the file content of lesson " +
                    "{}, but does not have access to it", user, lessonId);
            throw new InvalidCourseAccessException();
        }

        try {
            return lessonStorageService.getLessonFile(lessonId);
        } catch (FileNotFoundException e) {
            log.error("File not found: {}", e.getMessage());
            throw e;
        }
    }
}
