package com.example.usermanagementservice.api;

import com.example.usermanagementservice.config.JwtTokenProvider;
import com.example.usermanagementservice.model.AuthenticationRequest;
import com.example.usermanagementservice.model.RegistrationRequest;
import com.example.usermanagementservice.model.Role;
import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/auth/signin")
    public ResponseEntity<?> signin(@RequestBody AuthenticationRequest data) {
        log.info("login user {}", data);
        try {
            String username = data.getUsername();
            String password = data.getPassword();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authenticationToken);

            User user = userService.findByUsername(username).orElseThrow(() ->
                    new UsernameNotFoundException(String.format("Username %s not found", username)));

            String token = jwtTokenProvider.createToken(username, user.getRole().name());

            Map<Object, Object> model = new HashMap<>();
            model.put("user", new User(user.getId(), user.getName(), username));
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> saveUser(@RequestBody RegistrationRequest data) {
        log.info("save user {}", data);

        User user = new User();
        user.setName(data.getName());
        user.setUsername(data.getUsername());
        user.setPassword(data.getPassword());

        if (userService.findByUsername(data.getUsername()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            user.setRole(Role.ROLE_USER);
            user.setEnabled(true);
        }
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

}
