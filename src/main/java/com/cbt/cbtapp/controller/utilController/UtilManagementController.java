package com.cbt.cbtapp.controller.utilController;


import com.cbt.cbtapp.service.lessonService.LessonScoreService;
import com.cbt.cbtapp.service.lessonService.LessonStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UtilManagementController {

    @Autowired
    private LessonStudyService lessonStudyService;


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

    @GetMapping("/get_students_with_highest-scores")
    public ResponseEntity<?> getStudentsWithHighestScores() {
        return new ResponseEntity<>(lessonScoreService.getStudentsWithHighestScores(),HttpStatus.OK);
    }


    @GetMapping("/get_all_available_languages")
    private ResponseEntity<?> getAllLanguages(){
        return new ResponseEntity<>(lessonStudyService.getAllLanguages(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/get_all_courses")
    private ResponseEntity<?> getAllCourses(){
        return new ResponseEntity<>(lessonStudyService.getAllCourse(), HttpStatus.ACCEPTED);
    }


}
