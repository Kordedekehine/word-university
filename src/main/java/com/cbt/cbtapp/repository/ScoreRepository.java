package com.cbt.cbtapp.repository;

import com.cbt.cbtapp.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score,Long> {



    List<Score> findByStudentId(Long id);
}
