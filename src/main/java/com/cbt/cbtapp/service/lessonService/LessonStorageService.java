package com.cbt.cbtapp.service.lessonService;


import com.cbt.cbtapp.exception.lessons.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class LessonStorageService implements ILessonStorageService{

    private static final String LESSON_PATH = "C:\\codes\\cbt-app\\lessons/lesson%d.pdf";


    @Transactional
    @Override
    public void storeLesson(MultipartFile file, Long lessonId) throws FileStorageException {
        try {
            if (file == null || file.isEmpty()) {
                throw new FileStorageException("Unable to save file! Kindly re-assess the file");
            }

            Path destinationFile = Paths.get(getLessonFilePath(lessonId));

            // Ensure the directory structure exists
            Files.createDirectories(destinationFile.getParent());

            try (InputStream inputStream = file.getInputStream()) {
                if (inputStream != null) {
                    // Replace an existing file in the case of an edit
                    Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    throw new FileStorageException("Input stream is null");
                }
            }
        } catch (IOException e) {
            log.error("STORAGE FAILURE - saving content of lesson {} failed", lessonId);
            throw new FileStorageException("Failed to store file.", e);
        } catch (FileStorageException e) {
            throw new FileStorageException("Failed to store file.",e);
        }
    }


    @Override
    public byte[] getLessonFile(Long lessonId) throws IOException {
        Path pdfPath = Paths.get(getLessonFilePath(lessonId) );  // Add ".pdf" extension

        if (!Files.exists(pdfPath)) {
            throw new FileNotFoundException("Lesson file not found at: " + pdfPath);
        }

        return Files.readAllBytes(pdfPath);
    }


   private String getLessonFilePath(Long lessonId) {
        String path = String.format(LESSON_PATH, lessonId);
        log.info("Constructed Path: {}", Paths.get(path).toAbsolutePath());
        return path;
    }

}
