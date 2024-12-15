package com.dbProject.joongo.global.interceptor;

import com.dbProject.joongo.global.LoginConst;
import com.dbProject.joongo.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        } // 하.. cors 때문에 preflight 요청을 보냄 거기는 토큰이 비워져있니깐 해당 인터셉터를 항상 통과 못했지..
        String token = request.getHeader(LoginConst.AUTH_HEADER);
        log.info("token: {}", token);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized\", \"code\": \"login Please..\"}");
            log.error("Auth Error: {}", request.getRequestURI());
            return false;
        }
        return true;
    }
}
