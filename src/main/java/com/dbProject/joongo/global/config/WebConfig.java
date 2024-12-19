//package com.dbProject.joongo.global.config;
//
//import com.dbProject.joongo.global.interceptor.LoginCheckInterceptor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@RequiredArgsConstructor
//public class WebConfig implements WebMvcConfigurer {
//
//    private final LoginCheckInterceptor loginCheckInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginCheckInterceptor)
////                .excludePathPatterns("/api/users/**")
////                .excludePathPatterns("/api/categories/**")
//                .excludePathPatterns("/api/auth/**")
//                .excludePathPatterns("/health")
//                .excludePathPatterns("/favicon.ico")
//                .excludePathPatterns("/api/products/")
//                .excludePathPatterns("/api/products/{categoryId}")
//                .excludePathPatterns(
//                        "/swagger-ui/**",
//                        "/swagger-resources/**",
//                        "/v3/api-docs/**",
//                        "/v2/api-docs/**",
//                        "/webjars/**",
//                        "/actuator/**"); //Swagger 용
//                // 인가 필요없는 API URI 추가해주세요.
//    }
//}
