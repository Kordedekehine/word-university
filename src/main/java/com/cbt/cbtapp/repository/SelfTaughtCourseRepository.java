package com.cbt.cbtapp.repository;


import com.cbt.cbtapp.model.SelfTaughtCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfTaughtCourseRepository extends JpaRepository<SelfTaughtCourse,Long> {

}
