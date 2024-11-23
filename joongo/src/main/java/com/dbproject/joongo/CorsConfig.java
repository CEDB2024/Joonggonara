package com.dbproject.joongo;  // 적절한 패키지명으로 변경하세요.

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 URL 패턴에 대해
        registry.addMapping("/**")
                // 허용할 오리진 지정
                .allowedOrigins("http://localhost:3000")
                // 허용할 HTTP 메소드 지정
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 허용할 헤더 지정
                .allowedHeaders("*")
                // 크레덴셜 허용 설정
                .allowCredentials(true);
    }
}
