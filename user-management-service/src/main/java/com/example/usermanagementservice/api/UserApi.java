package com.example.usermanagementservice.api;

import com.example.usermanagementservice.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;
    private final DiscoveryClient discoveryClient;

    private final Environment env;

    @Value("${spring.application.name}")
    private String serviceId;

    @GetMapping("/service/port")
    public String getPort() {
        log.debug("get port");
        return "Service port number : " + env.getProperty("local.server.port");
    }

    @GetMapping("/service/instances")
    public ResponseEntity<?> getInstances() {
        log.debug("get instances");
        return new ResponseEntity<>(discoveryClient.getInstances(serviceId), HttpStatus.OK);
    }

    @GetMapping("/service/services")
    public ResponseEntity<?> getServices() {
        log.debug("get services");
        return new ResponseEntity<>(discoveryClient.getServices(), HttpStatus.OK);
    }

    @PostMapping("/service/names")
    public ResponseEntity<?> getNamesOfUsers(@RequestBody List<Long> idList) {
        log.debug("get names in list {}", idList);
        return ResponseEntity.ok(userService.findUsers(idList));
    }

    @GetMapping("/service/test")
    public ResponseEntity<?> test() {
        log.info("get test");
        return ResponseEntity.ok("It is working...");
    }
}
