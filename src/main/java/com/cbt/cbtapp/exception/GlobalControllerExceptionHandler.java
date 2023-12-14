package com.cbt.cbtapp.exception;

import com.cbt.cbtapp.exception.authentication.*;
import com.cbt.cbtapp.exception.lessons.DuplicateWordToLearnException;
import com.cbt.cbtapp.exception.lessons.NoWordsToLearnException;
import com.cbt.cbtapp.exception.students.CourseNotFoundException;
import com.cbt.cbtapp.exception.students.DuplicateEnrollmentException;
import com.cbt.cbtapp.exception.students.InvalidCourseAccessException;
import com.cbt.cbtapp.exception.students.LanguageNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @Getter
    @AllArgsConstructor
    public static class ExceptionResponse {
        private final List<String> messages;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ExceptionResponse handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return new ExceptionResponse(
                ex.getBindingResult()
                        .getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler(DuplicateUsernameException.class)
    public @ResponseBody
    ExceptionResponse handleDuplicateUsernameException(
            Exception ex) {
        return new ExceptionResponse(List.of("This username is already taken. Please specify " +
                "another one!"));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)  // 401
    @ExceptionHandler(AuthenticationRequiredException.class)
    public @ResponseBody
    ExceptionResponse handleAuthenticationRequiredException(
            Exception ex) {
        return new ExceptionResponse(List.of("You must be authenticated to access this " +
                "functionality!"));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)  // 401
    @ExceptionHandler(AccessRestrictedToTeachersException.class)
    public @ResponseBody
    ExceptionResponse handleAccessRestrictedToTeachersException(
            Exception ex) {
        return new ExceptionResponse(List.of("You must be a Teacher to access this " +
                "functionality!"));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)  // 401
    @ExceptionHandler(AccessRestrictedToStudentsException.class)
    public @ResponseBody
    ExceptionResponse handleAccessRestrictedToStudentsException(
            Exception ex) {
        return new ExceptionResponse(List.of("You must be a Student to access this " +
                "functionality!"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(LanguageNotFoundException.class)
    public @ResponseBody
    ExceptionResponse handleLanguageNotFoundException(
            Exception ex) {
        return new ExceptionResponse(List.of("This language is not supported!"));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)  // 401
    @ExceptionHandler(InvalidCourseAccessException.class)
    public @ResponseBody
    ExceptionResponse handleInvalidCourseAccessException(
            Exception ex) {
        return new ExceptionResponse(List.of("You do not have access to this course's data"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(NoWordsToLearnException.class)
    public @ResponseBody
    ExceptionResponse handleNoWordsToLearnException(
            Exception ex) {
        return new ExceptionResponse(List.of("No exercise can be generated, because you do not " +
                "have any unknown words for this lesson. Good Job! :)"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(IncorrectPasswordException.class)
    public @ResponseBody
    ExceptionResponse handleIncorrectPasswordException(
            Exception ex) {
        return new ExceptionResponse(List.of("Incorrect Password! Kindly input the correct password :)"));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(CourseNotFoundException.class)
    public @ResponseBody
    ExceptionResponse handleCourseNotFoundException(
            Exception ex) {
        return new ExceptionResponse(List.of("Course not Found! Please recheck your input :)"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(PasswordMismatchException.class)
    public @ResponseBody
    ExceptionResponse handlePasswordMismatchException(
            Exception ex) {
        return new ExceptionResponse(List.of("Error Confirming Password! Check if the new password is same" +
                "as the confirm passsword :)"));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler(DuplicateEnrollmentException.class)
    public @ResponseBody
    ExceptionResponse handleDuplicateEnrollmentException(
            Exception ex) {
        return new ExceptionResponse(List.of("You can't get enrolled in a course that you are " +
                "already enrolled in."));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler(DuplicateWordToLearnException.class)
    public @ResponseBody
    ExceptionResponse handleDuplicateWordToLearnException(
            Exception ex) {
        return new ExceptionResponse(List.of("You have already added this unknown word."));
    }
}
