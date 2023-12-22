package com.cbt.cbtapp.service.lessonService;

import com.cbt.cbtapp.dto.UserResponseDto;

import java.util.List;

public interface ILessonScoreService {

    List<UserResponseDto> getStudentsSortedByScore();

    int calculateStandingScore(Long studentId);

     List<UserResponseDto> getAllStudents();

     List<UserResponseDto> getAllTeachers();

    UserResponseDto getBestStudent();
}
