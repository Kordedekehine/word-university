package com.cbt.cbtapp.repository;

import com.cbt.cbtapp.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score,Long> {

    // Find the highest score for each student
    //Inside score pick student
    //Inside score pick highest points
    //then use the highest point to group the student
    @Query("SELECT s.student.id, MAX(s.points) FROM Score s GROUP BY s.student.id")
    List<Object[]> findStudentsWithHighestScores();


    List<Score> findByStudentId(Long id);
}
