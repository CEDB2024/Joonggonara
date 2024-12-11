package com.dbProject.joongo.service;

import com.dbProject.joongo.domain.User;
import com.dbProject.joongo.dto.auth.AuthRequest;
import com.dbProject.joongo.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public Integer register(AuthRequest.RegisterRequest registerInfo) {
        try {
            // 사용자 조회 및 중복 체크
            User user = userService.getUserByEmail(registerInfo.getEmail());
            if (user != null) {
                throw new IllegalArgumentException("[Register] Duplicated Email: " + registerInfo.getEmail());
            }
            userService.addUser(registerInfo);
            return userService.getLastInsertId();
        } catch (Exception e) {
            log.error("register error: {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
