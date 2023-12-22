package com.cbt.cbtapp.controller.studentController;


import com.cbt.cbtapp.dto.WordLearnAnswersDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToStudentsException;
import com.cbt.cbtapp.exception.lessons.LessonNotFoundException;
import com.cbt.cbtapp.exception.lessons.WordToLearnNotFoundException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;
import com.cbt.cbtapp.service.lessonService.ILessonPracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class ExercisePracticeController {

    @Autowired
    private ILessonPracticeService lessonPracticeService;

    @GetMapping(value = "/get_word_problem_question")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<?> getWordProblemQuestion(@RequestParam Long lessonId) throws AccessRestrictedToStudentsException, LessonNotFoundException, InvalidCourseAccessException, WordToLearnNotFoundException {

        return new ResponseEntity<>(lessonPracticeService.solveWordQuestion(lessonId),HttpStatus.OK);
    }


    @PostMapping(value = "/answer_word_problem_question")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<?> answerWordProblemQuestion(@RequestBody WordLearnAnswersDto wordLearnAnswersDto) throws AccessRestrictedToStudentsException, InvalidCourseAccessException, WordToLearnNotFoundException {

        return new ResponseEntity<>(lessonPracticeService.answerWordQuestion(wordLearnAnswersDto), HttpStatus.CREATED);
    }

}
