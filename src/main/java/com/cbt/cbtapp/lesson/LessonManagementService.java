package com.cbt.cbtapp.lesson;

import com.cbt.cbtapp.dto.LessonResponseDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToStudentsException;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToTeachersException;
import com.cbt.cbtapp.model.Lesson;
import com.cbt.cbtapp.model.User;
import com.cbt.cbtapp.repository.LessonRepository;
import com.cbt.cbtapp.security.AuthenticationService;
import com.cbt.cbtapp.verifier.RightVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public byte[] getLessonsFile(Long lessonId) throws IOException {

        User user;

        try{
            user = authenticationService.getCurrentStudent();

        }catch (RuntimeException exception){
            try{
               user = authenticationService.getCurrentTeacher();
            }catch (RuntimeException | AccessRestrictedToTeachersException e){
                throw new RuntimeException("Unknown user type");
            }
        } catch (AccessRestrictedToStudentsException e) {
            throw new RuntimeException(e);
        }

        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (lesson.isEmpty()) {
            throw new RuntimeException("Lesson does not exist");
        }

        if (!rightVerifier.hasAccessToTheDataOf(user, lesson.get())) {
            throw new RuntimeException("You have no access to this data");
        }

        return lessonStorageService.getLessonFile(lessonId);
    }


    public LessonResponseDto getLessonData(Long lessonId) {
        User user;

        try {
            user = authenticationService.getCurrentStudent();
        } catch (RuntimeException | AccessRestrictedToStudentsException e) {
            try {
                user = authenticationService.getCurrentTeacher();
            } catch (RuntimeException | AccessRestrictedToTeachersException exception) {
                throw new IllegalStateException("Unknown user type");
            }
        }

        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (lesson.isEmpty()) {
            throw new RuntimeException("Lesson does not exist");
        }

        if (!rightVerifier.hasAccessToTheDataOf(user, lesson.get())) {
            throw new RuntimeException("You have no access to this data");
        }

        return new LessonResponseDto(
                lesson.get().getTitle(),
                lesson.get().getCourse().getId(),
                lesson.get().getIndexInsideCourse(),
                lesson.get().getCourse().getTitle());
    }


}
