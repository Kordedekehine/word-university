package com.cbt.cbtapp.controller.studentController;

import com.cbt.cbtapp.dto.EnrolledCourseDto;
import com.cbt.cbtapp.dto.SelfTaughtCourseDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToStudentsException;
import com.cbt.cbtapp.exception.lessons.FileStorageException;
import com.cbt.cbtapp.exception.students.CourseNotFoundException;
import com.cbt.cbtapp.exception.students.DuplicateEnrollmentException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;
import com.cbt.cbtapp.exception.students.LanguageNotFoundException;
import com.cbt.cbtapp.service.student.IStudentCourseAndLessonManageService;
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
public class StudentCourseManagementController {

    @Autowired
    private IStudentCourseAndLessonManageService service;

    @PostMapping("/create_selfTaught_course")
    public ResponseEntity<?> createSelfTaughtCourse(@Valid @RequestBody SelfTaughtCourseDto selfTaughtCourseDto) throws LanguageNotFoundException, AccessRestrictedToStudentsException {
    return new ResponseEntity<>(service.createSelfTaughtCourse(selfTaughtCourseDto), HttpStatus.CREATED);
    }

    @PostMapping(value = "/add_new_self_taught_lesson", consumes =
            MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addNewSelfTaughtLesson(@RequestParam("file") MultipartFile file, @RequestParam(
            "title") String title, @RequestParam Long courseId) throws AccessRestrictedToStudentsException, CourseNotFoundException, InvalidCourseAccessException, FileStorageException {

    return new ResponseEntity<>(service.saveNewSelfTaughtLesson(courseId, title, file),HttpStatus.CREATED);
    }

    @GetMapping("/get_all_enrolled_courses")
    public ResponseEntity<List<EnrolledCourseDto>> getAllEnrolledCourses() throws AccessRestrictedToStudentsException {

        return new ResponseEntity<>(service.getAllEnrolledCourses(),HttpStatus.OK);
    }

    @GetMapping("/get_enrolled_course_data/{courseId}")
    public ResponseEntity<?> getEnrolledCourseData(@RequestParam Long courseId) throws AccessRestrictedToStudentsException, InvalidCourseAccessException, CourseNotFoundException {
        return new ResponseEntity<>(service.getEnrolledCourseData(courseId),HttpStatus.OK);
    }

    @PostMapping("/join_supervised_course")
    public ResponseEntity<?> joinSupervisedCourse(@RequestBody Integer joiningCode) throws AccessRestrictedToStudentsException,
            CourseNotFoundException, DuplicateEnrollmentException {

     return new ResponseEntity<>(service.joinSupervisedCourse(joiningCode),HttpStatus.OK);
    }

}
