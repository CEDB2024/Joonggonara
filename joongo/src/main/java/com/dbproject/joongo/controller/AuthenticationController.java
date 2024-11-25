package com.dbproject.joongo.controller;

import com.dbproject.joongo.dto.LoginRequest;
import com.dbproject.joongo.security.JwtTokenProvider;
import com.dbproject.joongo.service.UserService;
import com.dbproject.joongo.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Invalid email or password"));
        }

        // 토큰 생성
        String token = jwtTokenProvider.createToken(user.getEmail());
        return ResponseEntity.ok(Map.of(
                "success", true,
                "token", token
        ));
    }

    // 토큰 검증 (React에서 호출 가능)
    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String token) {
        if (jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.ok(Map.of("valid", true));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("valid", false));
        }
    }

    // 사용자 등록 (회원가입)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            userService.addUser(user);
            return ResponseEntity.ok(Map.of("success", true, "message", "User registered successfully!"));
        } catch (Exception e) {
            e.printStackTrace(); // 디버깅을 위해 예외를 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "Registration failed."));
        }
    }
    
    
}
