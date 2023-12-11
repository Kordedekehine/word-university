package com.cbt.cbtapp.lesson;

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
@RequestMapping("/lesson_management")
public class LessonManagementController {

    @Autowired
    private LessonManagementService lessonManagementService;

    @GetMapping("/get_lesson_data")
    public ResponseEntity<?> getLessonData(@RequestParam Long lessonId)  {
        try {
            return new ResponseEntity<>(lessonManagementService.getLessonData(lessonId), HttpStatus.ACCEPTED);
        }
        catch (RuntimeException exception){
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST );
        }
    }

    @GetMapping(value = "/get_lesson_file", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getLessonFile(@RequestParam Long lessonId) throws IOException {

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
