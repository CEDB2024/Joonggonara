package com.dbProject.joongo.controller;

import com.dbProject.joongo.dto.auth.AuthRequest;
import com.dbProject.joongo.dto.auth.AuthRequest.LoginRequest;
import com.dbProject.joongo.global.LoginConst;
import com.dbProject.joongo.global.PasswordUtils;
import com.dbProject.joongo.security.JwtTokenProvider;
import com.dbProject.joongo.service.UserService;
import com.dbProject.joongo.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // 사용자 조회
        User user = userService.getUserByEmail(loginRequest.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        // 비밀번호 검증
        boolean isPasswordMatch = PasswordUtils.matchPassword(loginRequest.getPassword(), user.getUserPassword());
        if (!isPasswordMatch) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }


        // 토큰 생성
        String token = jwtTokenProvider.createToken(user.getEmail());
        log.info("created token {}", token);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "token", token
        ));
    }

    // 토큰 검증 (React에서 호출 가능)
    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader(LoginConst.AUTH_HEADER) String token) {
        if (jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.ok(Map.of("valid", true));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("valid", false));
        }
    }

    // 사용자 등록 (회원가입)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest.RegisterRequest registerRequest) {
        try {
            userService.addUser(registerRequest);
            return ResponseEntity.ok(Map.of("success", true, "message", "User registered successfully!"));
        } catch (Exception e) {
            e.printStackTrace(); // 디버깅을 위해 예외를 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "Registration failed."));
        }
    }

    // JWT에서 이메일 정보 추출
    @GetMapping("/email")
    public ResponseEntity<?> getEmailFromToken(@RequestHeader(LoginConst.AUTH_HEADER) String token) {
        if (token == null || !token.startsWith(LoginConst.AUTH_HEADER_PREFIX)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Invalid token format"));
        }

        try {
            String jwtToken = token.substring(LoginConst.AUTH_HEADER_PREFIX.length()); // "Bearer " 제거
            String email = jwtTokenProvider.getEmailFromToken(jwtToken);
            return ResponseEntity.ok(Map.of("success", true, "email", email));
        } catch (Exception e) {
            log.error("Error extracting email from token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Invalid or expired token"));
        }
    }
}
