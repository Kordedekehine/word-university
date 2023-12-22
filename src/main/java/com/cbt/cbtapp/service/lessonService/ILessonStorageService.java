package com.cbt.cbtapp.service.lessonService;

import com.cbt.cbtapp.exception.lessons.FileStorageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ILessonStorageService {

    void storeLesson(MultipartFile file, Long lessonId) throws FileStorageException;

     byte[] getLessonFile(Long lessonId) throws IOException;
}
