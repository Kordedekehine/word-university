package com.cbt.cbtapp.repository;

import com.cbt.cbtapp.model.SelfTaughtLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfTaughtLessonRepository extends JpaRepository<SelfTaughtLesson,Long> {

}
