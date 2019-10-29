package com.example.coursemanagementservice.repository;

import com.example.coursemanagementservice.model.Course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
