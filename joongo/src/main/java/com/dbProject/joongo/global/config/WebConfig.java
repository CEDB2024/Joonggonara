package com.dbProject.joongo.global.config;

import com.dbProject.joongo.global.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")  // 적용할 URL 패턴
                .order(1)
                .excludePathPatterns("/api/auth/**", "/swagger-ui/**", "/v3/**"); // 제외할 URL 패턴
    }
}
