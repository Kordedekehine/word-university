package com.cbt.cbtapp.controller.dataController;

import com.cbt.cbtapp.exception.authentication.AccessRestrictedToStudentsException;
import com.cbt.cbtapp.exception.lessons.LessonNotFoundException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;
import com.cbt.cbtapp.service.lessonService.LessonManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
public class LessonManagementController {


    @Autowired
    private LessonManagementService lessonManagementService;

    @GetMapping("/get_lesson_data")
    public ResponseEntity<?> getLessonData(@RequestParam Long lessonId)  {
        try {
            return new ResponseEntity<>(lessonManagementService.getLessonData(lessonId), HttpStatus.ACCEPTED);
        }
        catch (RuntimeException | LessonNotFoundException | InvalidCourseAccessException exception){
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST );
        }
    }

    @GetMapping(value = "/get_student_lesson_file", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getLessonFile(@RequestParam Long lessonId) throws IOException, LessonNotFoundException, InvalidCourseAccessException, AccessRestrictedToStudentsException {

        byte[] file = lessonManagementService.getLessonsFile(lessonId);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=lesson.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }
}
