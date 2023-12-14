package com.cbt.cbtapp.controller.studentController;

import com.cbt.cbtapp.dto.SaveUnknownWordsDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToStudentsException;
import com.cbt.cbtapp.exception.lessons.LessonNotFoundException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;
import com.cbt.cbtapp.service.lessonService.LessonStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/v1/auth")
public class LessonStudyController {

    @Autowired
    private LessonStudyService lessonStudyService;

    @PostMapping("/saveUnknownWords")
    private ResponseEntity<?> saveUnknownWords(@RequestBody SaveUnknownWordsDto saveUnknownWordsDto) throws AccessRestrictedToStudentsException, LessonNotFoundException, InvalidCourseAccessException {
            return new ResponseEntity<>(lessonStudyService.saveUnknownWord(saveUnknownWordsDto),HttpStatus.ACCEPTED);
    }

    @GetMapping(value="/get_lesson_notes_in_pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getLessonVocabularyInPdf(@RequestParam Long lessonId) throws AccessRestrictedToStudentsException, LessonNotFoundException, InvalidCourseAccessException {

        ByteArrayInputStream vocabularyPDF = lessonStudyService.getLessonVocabularyInPdf(lessonId);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=vocabulary.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(vocabularyPDF));

    }

}


