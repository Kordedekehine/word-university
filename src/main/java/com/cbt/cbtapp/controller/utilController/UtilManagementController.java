package com.cbt.cbtapp.controller.utilController;


import com.cbt.cbtapp.service.lessonService.LessonScoreService;
import com.cbt.cbtapp.service.lessonService.LessonStudyService;
import com.cbt.cbtapp.service.userService.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UtilManagementController {

    @Autowired
    private LessonStudyService lessonStudyService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private LessonScoreService lessonScoreService;

    @GetMapping("/get_student_total_score/{studentId}")
    public ResponseEntity<?> getStudentScores(@PathVariable Long studentId) {
        return new ResponseEntity<>(lessonScoreService.getStudentScores(studentId), HttpStatus.OK);
    }

    @GetMapping("/get_current_best_student")
    public ResponseEntity<?> getBestStudent() {
        return new ResponseEntity<>(lessonScoreService.getBestStudent(),HttpStatus.OK);
    }

    @GetMapping("/get_all_student")
    public ResponseEntity<?> getAllStudent() {
        return new ResponseEntity<>(lessonScoreService.getAllStudents(),HttpStatus.OK);
    }

    @GetMapping("/get_all_teacher")
    public ResponseEntity<?> getAllTeacher() {
        return new ResponseEntity<>(lessonScoreService.getAllTeachers(),HttpStatus.OK);
    }


    @GetMapping("/get_all_available_languages")
    private ResponseEntity<?> getAllLanguages(){
        return new ResponseEntity<>(lessonStudyService.getAllLanguages(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/get_all_courses")
    private ResponseEntity<?> getAllCourses(){
        return new ResponseEntity<>(lessonStudyService.getAllCourse(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/get_nr_of_users")
    private ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(loginService.getAllUsers(), HttpStatus.ACCEPTED);
    }

}
