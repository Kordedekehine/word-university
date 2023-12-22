package com.cbt.cbtapp.controller.utilController;


import com.cbt.cbtapp.service.lessonService.ILessonScoreService;
import com.cbt.cbtapp.service.lessonService.ILessonStudyService;
import com.cbt.cbtapp.service.userService.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UtilManagementController {

    @Autowired
    private ILessonStudyService lessonStudyService;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private ILessonScoreService lessonScoreService;

    @GetMapping("/get_student_total_score/{studentId}")
    public ResponseEntity<?> getStudentScores(@PathVariable Long studentId) {
        return new ResponseEntity<>(lessonScoreService.calculateStandingScore(studentId), HttpStatus.OK);
    }

    @GetMapping("/get_current_students_sorted_by_score")
    public ResponseEntity<?> getStudentsSortedByScore() {
        return new ResponseEntity<>(lessonScoreService.getStudentsSortedByScore(),HttpStatus.OK);
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
