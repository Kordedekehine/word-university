package com.cbt.cbtapp.repository;

import com.cbt.cbtapp.model.CourseEnrollment;
import com.cbt.cbtapp.model.Lesson;
import com.cbt.cbtapp.model.WordToLearn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordToLearnRepository extends JpaRepository<WordToLearn,Long> {

    Optional<WordToLearn> findByLessonAndCourseEnrollmentAndOriginalWord(Lesson lesson,
                                                                         CourseEnrollment courseEnrollment,
                                                                         String word);

     List<WordToLearn> findByLesson(Lesson lesson);

    List<WordToLearn> findByLessonAndCourseEnrollmentAndCollectedPoints(Lesson lesson, CourseEnrollment courseEnrollment,
                                                                                int targetPoints);

}
