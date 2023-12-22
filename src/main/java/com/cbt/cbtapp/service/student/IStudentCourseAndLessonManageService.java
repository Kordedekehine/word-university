package com.cbt.cbtapp.service.student;

import com.cbt.cbtapp.dto.*;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToStudentsException;
import com.cbt.cbtapp.exception.lessons.FileStorageException;
import com.cbt.cbtapp.exception.students.CourseNotFoundException;
import com.cbt.cbtapp.exception.students.DuplicateEnrollmentException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;
import com.cbt.cbtapp.exception.students.LanguageNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IStudentCourseAndLessonManageService {

    SelfTaughtLessonDto createSelfTaughtCourse(SelfTaughtCourseDto selfTaughtCourseDTO) throws AccessRestrictedToStudentsException, LanguageNotFoundException;

    List<EnrolledCourseDto> getAllEnrolledCourses() throws AccessRestrictedToStudentsException;

    SelfTaughtLessonResponseDto saveNewSelfTaughtLesson(Long courseId, String title, MultipartFile file) throws AccessRestrictedToStudentsException, CourseNotFoundException, InvalidCourseAccessException, FileStorageException;

    ExtendedEnrolledCourseDto getEnrolledCourseData(Long courseId) throws AccessRestrictedToStudentsException, InvalidCourseAccessException, CourseNotFoundException;

    String joinSupervisedCourse(Integer joiningCode) throws CourseNotFoundException, AccessRestrictedToStudentsException, DuplicateEnrollmentException
}
