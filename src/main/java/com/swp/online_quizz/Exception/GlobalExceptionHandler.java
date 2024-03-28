package com.swp.online_quizz.Exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = IllegalArgumentException.class)
    public String handleIllegalArgumentException(HttpServletRequest req, IllegalArgumentException e) {
        String errorMessage = e.getMessage();
        if (errorMessage.equals("Cannot access this quiz")) {
            req.setAttribute("errorMessage", errorMessage);
        }
        return "notFoundQuiz";
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public String handleIEntityNotFoundException(HttpServletRequest req, EntityNotFoundException e) {
        String errorMessage = e.getMessage();
        if (errorMessage.equals("Quiz not found")) {
            req.setAttribute("errorMessage", errorMessage);
        }
        return "notFoundQuiz";
    }

    @ExceptionHandler(value = NullPointerException.class)
    public String handleINullPointerException(HttpServletRequest req, NullPointerException e) {
        String errorMessage = e.getMessage();
        if (errorMessage.equals("File excel not follow template")) {
            req.setAttribute("errorMessage", errorMessage);
        }
        return "notFoundQuiz";
    }

    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception e) {
        return "notFoundQuiz";
    }
}