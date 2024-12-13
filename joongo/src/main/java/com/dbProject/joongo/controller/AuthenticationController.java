package com.dbProject.joongo.controller;

import com.dbProject.joongo.dto.auth.AuthRequest;
import com.dbProject.joongo.dto.auth.AuthRequest.LoginRequest;
import com.dbProject.joongo.global.LoginConst;
import com.dbProject.joongo.global.PasswordUtils;
import com.dbProject.joongo.security.JwtTokenProvider;
import com.dbProject.joongo.service.AuthService;
import com.dbProject.joongo.service.UserService;
import com.dbProject.joongo.domain.User;
import javax.security.auth.login.LoginException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws LoginException {
        // 사용자 조회
        User user = userService.getUserByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new LoginException("[Auth] Invalid Email: " + loginRequest.getEmail());
        }

        // 비밀번호 검증
        boolean isPasswordMatch = PasswordUtils.matchPassword(loginRequest.getPassword(), user.getUserPassword());
        if (!isPasswordMatch) {
            throw new LoginException("[Auth] Invalid Password");
        }

        // 토큰 생성
        String token = jwtTokenProvider.createToken(user.getEmail());
        log.info("created token {}", token);

        int userId = user.getUserId();

        return ResponseEntity.ok(Map.of(
                "success", true,
                "token", token,
                "userId", userId
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
    /* FIXME : Transactional을 controller에 달면 잘 동작함
                service에 달면 제대로 동작을 안함 이유는 ?
                EXCEPTION이 전파되는 정도때문이었음
                CONTROLLER에서는 TRY CATCH로 예외르 잡아줘서
                MYSQL에서 보낸 에러를 처리해서 롤백이 안일어남..? 맞나
    * */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest.RegisterRequest registerRequest) {
        log.info("Register method called with request: {}", registerRequest);
        try {
            authService.register(registerRequest);
            return ResponseEntity.ok(Map.of("success", true, "registry"  , "User registered successfully!"));
        } catch (Exception e) {// 디버깅을 위해 예외를 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "Registration failed."));
        }
    }

    // JWT 에서 이메일 정보 추출
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
