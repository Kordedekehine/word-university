package com.cbt.cbtapp.repository;

import com.cbt.cbtapp.model.SupervisedLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisedLessonRepository extends JpaRepository<SupervisedLesson,Long> {


}
