package com.cbt.cbtapp.service.teacher;

import com.cbt.cbtapp.dto.ExtendedTaughtCourseDto;
import com.cbt.cbtapp.dto.SupervisedCourseDto;
import com.cbt.cbtapp.dto.TaughtCourseDto;
import com.cbt.cbtapp.exception.authentication.AccessRestrictedToTeachersException;
import com.cbt.cbtapp.exception.lessons.FileStorageException;
import com.cbt.cbtapp.exception.students.CourseNotFoundException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;
import com.cbt.cbtapp.exception.students.LanguageNotFoundException;
import com.cbt.cbtapp.model.SupervisedLesson;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ITeacherCourseAndLessonManagementService {

    ExtendedTaughtCourseDto createSupervisedCourse(SupervisedCourseDto supervisedCourseDto) throws LanguageNotFoundException, AccessRestrictedToTeachersException;

    List<TaughtCourseDto> getAllTaughtCourses() throws AccessRestrictedToTeachersException;

    SupervisedLesson saveNewSupervisedLesson(Long courseId, String title, MultipartFile file) throws CourseNotFoundException, InvalidCourseAccessException, AccessRestrictedToTeachersException, FileStorageException;

    ExtendedTaughtCourseDto getTaughtCourseData(Long courseId) throws InvalidCourseAccessException, CourseNotFoundException, AccessRestrictedToTeachersException;
}
