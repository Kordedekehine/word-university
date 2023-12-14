package com.cbt.cbtapp.service.lessonService;

import com.cbt.cbtapp.Utils.Utils;
import com.cbt.cbtapp.dto.StudentWithScoreDto;
import com.cbt.cbtapp.dto.UserResponseDto;
import com.cbt.cbtapp.model.Score;
import com.cbt.cbtapp.model.Student;
import com.cbt.cbtapp.model.Teacher;
import com.cbt.cbtapp.repository.ScoreRepository;
import com.cbt.cbtapp.repository.StudentRepository;
import com.cbt.cbtapp.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LessonScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public List<Score> getStudentScores(Long studentId)  {

        return scoreRepository.findByStudentId(studentId);
    }

    public int calculateStandingScore(Long studentId)  {

        List<Score> studentScores = getStudentScores(studentId);
        return studentScores.stream().mapToInt(Score::getPoints).sum();
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllStudents() {

        List<Student> students = studentRepository.findAll();
        //sort by created date
       // students.sort((c1, c2) -> c2.getCreated().compareTo(c1.getCreated()));
        return students.stream().map(this::mapStudentToResponse).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllTeachers() {

        List<Teacher> teachers = teacherRepository.findAll();
        teachers.sort((c1, c2) -> c2.getCreated().compareTo(c1.getCreated()));
        return teachers.stream().map(this::mapTeacherToResponse).collect(toList());
    }


    private UserResponseDto mapStudentToResponse(Student student) {
        return UserResponseDto.builder()
                .id(student.getId())
                .name(student.getUserName())
                .role(student.getCreateRole())
                .emailAddress(student.getEmail())
                .language(student.getNativeLanguage().toString())
             //   .created(Utils.timeAgo(student.getCreated()))
                .build();
    }

    private UserResponseDto mapTeacherToResponse(Teacher teacher) {
        return UserResponseDto.builder()
                .id(teacher.getId())
                .name(teacher.getUserName())
                .role(teacher.getCreateRole())
                .emailAddress(teacher.getEmail())
                .language(teacher.getNativeLanguage().toString())
              //    .created(Utils.timeAgo(teacher.getCreated()))
                .build();
    }

    public UserResponseDto getBestStudent() {

        List<UserResponseDto> allStudents = getAllStudents();

        // Find the student with the highest standing score
        return allStudents.stream()
                .max(Comparator.comparingInt(student -> calculateStandingScore(student.getId())))
                .orElse(null);
    }


    public List<StudentWithScoreDto> getStudentsWithHighestScores() {
        List<Object[]> results = scoreRepository.findStudentsWithHighestScores();

        List<StudentWithScoreDto> studentsWithScores = new ArrayList<>();

        for (Object[] result : results) {
            Student student = (Student) result[0];
            Integer highestScore = (Integer) result[1];

            studentsWithScores.add(new StudentWithScoreDto(student, highestScore));
        }

        return studentsWithScores;
    }
}
