package com.cbt.cbtapp.repository;


import com.cbt.cbtapp.model.Course;
import com.cbt.cbtapp.model.CourseEnrollment;
import com.cbt.cbtapp.model.Student;
import com.cbt.cbtapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

}
