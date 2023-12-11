package com.cbt.cbtapp.general;

import com.cbt.cbtapp.dto.CourseResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class GeneralController {

    @Autowired
    private GeneralCheckService generalCheckService;

    @GetMapping("/all_course")
    public ResponseEntity<List<CourseResponseDto>> getAllCourse() {
        return new ResponseEntity<>(generalCheckService.getAllCourse(), HttpStatus.ACCEPTED);
    }
}
