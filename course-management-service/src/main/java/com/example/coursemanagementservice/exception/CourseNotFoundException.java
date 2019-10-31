package com.example.coursemanagementservice.exception;

public class CourseNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Course %d not found!";

    public CourseNotFoundException(Long courseId) {
        super(String.format(MESSAGE, courseId));
    }

    public CourseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

}