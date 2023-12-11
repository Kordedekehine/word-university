package com.cbt.cbtapp.repository;

import com.cbt.cbtapp.model.SupervisedCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupervisedCourseRepository extends JpaRepository<SupervisedCourse,Long> {

    Optional<SupervisedCourse> findByJoiningCode(int joiningCode);
}
