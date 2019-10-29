package com.example.coursemanagementservice.service;

import com.example.coursemanagementservice.model.Course;
import com.example.coursemanagementservice.model.Transaction;

import java.util.List;

public interface CourseService {
    List<Course> allCourses();
    Course findCourseById(Long courseId);
    List<Transaction> findTransactionsOfUser(Long userId);
    List<Transaction> findTransactionsOfCourse(Long courseId);
    Transaction saveTransaction(Transaction transaction);
}
