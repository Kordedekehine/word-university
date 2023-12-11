package com.cbt.cbtapp.repository;

import com.cbt.cbtapp.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    Optional<Teacher> findByUserName(String name);

}
