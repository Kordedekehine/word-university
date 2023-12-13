package com.cbt.cbtapp.lesson;


import com.cbt.cbtapp.dto.WordLearnAnswersDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToStudentsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class LessonPracticeController {

    @Autowired
    private LessonPracticeService lessonPracticeService;

    @GetMapping(value = "/get_student_word_question")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<?> getWordQuestion(@RequestParam Long lessonId) throws AccessRestrictedToStudentsException {

        return new ResponseEntity<>(lessonPracticeService.solveQuestion(lessonId),HttpStatus.OK);
    }



    @PostMapping(value = "/answer_word_question")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<?> answerWordQuestion(@RequestBody WordLearnAnswersDto wordLearnAnswersDto) throws AccessRestrictedToStudentsException {

        return new ResponseEntity<>(lessonPracticeService.practiceVerifyDto(wordLearnAnswersDto), HttpStatus.CREATED);
    }

    @PostMapping(value = "/calculate_mark")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<?> calculateMark() throws AccessRestrictedToStudentsException {

        return new ResponseEntity<>(lessonPracticeService.calculateStudentMark(), HttpStatus.CREATED);
    }

}
