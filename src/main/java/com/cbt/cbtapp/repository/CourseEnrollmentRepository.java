package com.cbt.cbtapp.repository;


import com.cbt.cbtapp.model.Course;
import com.cbt.cbtapp.model.CourseEnrollment;
import com.cbt.cbtapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment,Long> {

    Optional<CourseEnrollment> findByCourseAndStudent(Course course, Student student);

    List<CourseEnrollment> findByStudent(Student student);
}
