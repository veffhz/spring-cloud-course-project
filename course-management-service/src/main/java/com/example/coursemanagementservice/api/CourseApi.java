package com.example.coursemanagementservice.api;

import com.example.coursemanagementservice.feign.UserClient;
import com.example.coursemanagementservice.model.Transaction;
import com.example.coursemanagementservice.service.CourseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CourseApi {

    private final UserClient userClient;
    private final CourseService courseService;
    private final DiscoveryClient discoveryClient;

    private final Environment env;

    @Value("${spring.application.name}")
    private String serviceId;

    @GetMapping("/service/port")
    public String getPort() {
        log.debug("get port");
        return "Service is working at port : " + env.getProperty("local.server.port");
    }

    @GetMapping("/service/instances")
    public ResponseEntity<?> getInstances() {
        log.debug("get instances");
        return ResponseEntity.ok(discoveryClient.getInstances(serviceId));
    }

    @GetMapping("/service/user/{userId}")
    public ResponseEntity<?> findTransactionsOfUser(@PathVariable Long userId) {
        log.info("get transactions by userId {}", userId);
        return ResponseEntity.ok(courseService.findTransactionsOfUser(userId));
    }

    @GetMapping("/service/all")
    public ResponseEntity<?> findAllCourses() {
        log.info("get all curses");
        return ResponseEntity.ok(courseService.allCourses());
    }

    @PostMapping("/service/enroll")
    public ResponseEntity<?> saveTransaction(@RequestBody Transaction transaction) {
        log.info("save transaction, {}", transaction);
        transaction.setDateOfIssue(LocalDateTime.now());
        transaction.setCourse(courseService.findCourseById(transaction.getCourse().getId()));
        return new ResponseEntity<>(courseService.saveTransaction(transaction), HttpStatus.CREATED);
    }

    @GetMapping("/service/course/{courseId}")
    public ResponseEntity<?> findStudentsOfCourse(@PathVariable Long courseId) {
        log.info("get students by userId {}", courseId);
        List<Transaction> transactions = courseService.findTransactionsOfCourse(courseId);
        if (CollectionUtils.isEmpty(transactions)) {
            return ResponseEntity.notFound().build();
        }
        List<Long> userIdList = transactions.parallelStream()
                .map(Transaction::getUserId)
                .collect(Collectors.toList());

        List<String> students = userClient.getUserNames(userIdList);
        return ResponseEntity.ok(students);
    }
}
