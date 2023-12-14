package com.cbt.cbtapp.controller.teacherController;

import com.cbt.cbtapp.dto.SupervisedCourseDto;
import com.cbt.cbtapp.dto.TaughtCourseDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToTeachersException;
import com.cbt.cbtapp.exception.students.CourseNotFoundException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;
import com.cbt.cbtapp.exception.students.LanguageNotFoundException;
import com.cbt.cbtapp.service.teacher.TeacherCourseAndLessonManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class TeacherCourseManagementController {

    @Autowired
    private TeacherCourseAndLessonManagementService service;

    @PostMapping("/create_supervised_course")
    public ResponseEntity<?> createSelfTaughtCourse(@Valid @RequestBody SupervisedCourseDto supervisedCourseDTO) throws LanguageNotFoundException, AccessRestrictedToTeachersException, LanguageNotFoundException {

        return new ResponseEntity<>(service.createSupervisedCourse(supervisedCourseDTO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/add_new_supervised_lesson", consumes =
            MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addNewSelfTaughtLesson(@RequestParam("file") MultipartFile file, @RequestParam(
            "title") String title, @RequestParam Long courseId) throws CourseNotFoundException, AccessRestrictedToTeachersException, InvalidCourseAccessException {
       return new ResponseEntity<>(service.saveNewSupervisedLesson(courseId, title, file),HttpStatus.CREATED);
    }

    @GetMapping("/get_all_taught_courses")
    public ResponseEntity<List<TaughtCourseDto>> getAllTaughtCourses() throws AccessRestrictedToTeachersException {

        return new ResponseEntity<>(service.getAllTaughtCourses(),HttpStatus.OK);
    }

    @GetMapping("/get_specific_taught_course_data")
    public ResponseEntity<?> getTaughtCourseData(Long courseId) throws InvalidCourseAccessException, CourseNotFoundException, AccessRestrictedToTeachersException {

        return new ResponseEntity<>(service.getTaughtCourseData(courseId),HttpStatus.OK);
    }
}
