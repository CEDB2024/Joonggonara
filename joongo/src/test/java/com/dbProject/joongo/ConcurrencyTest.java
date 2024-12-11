package com.dbProject.joongo;

import com.dbProject.joongo.controller.AuthenticationController;
import com.dbProject.joongo.dto.auth.AuthRequest;
import com.dbProject.joongo.service.UserService;
import jakarta.validation.constraints.Size.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class ConcurrencyTest {

    @Autowired
    private AuthenticationController authenticationController;

    @Test
    public void 회원가입_기존_아이디_검증_테스트() throws InterruptedException {
        // given
        int threadNum = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        AuthRequest.RegisterRequest newMember1 = new AuthRequest.RegisterRequest(null,
                "randomUser1", "nickname1", "password1", "random1@example.com",
                "1234", "1234", "Seoul", "USER", "ACTIVE", 5000L
        );

        AuthRequest.RegisterRequest newMember2 = new AuthRequest.RegisterRequest(null,
                "randomUser2", "nickname2", "password2", "random1@example.com",
                "1234", "1234", "Busan", "USER", "ACTIVE", 10000L
        );

        // when
        Future<?> submit1 = executorService.submit(() -> {
            authenticationController.register(newMember1);
        });

        Future<?> submit2 = executorService.submit(() -> {
            authenticationController.register(newMember2);
        });

        executorService.shutdown();

        boolean exceptionOccurred = false;

        // then
        try {
            submit1.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                exceptionOccurred = true;
                System.out.println("Exception in Thread 1: " + cause.getMessage());
            }
        }

        try {
            submit2.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                exceptionOccurred = true;
                System.out.println("Exception in Thread 2: " + cause.getMessage());
            }
        }

        Assertions.assertThat(exceptionOccurred)
                .as("하나의 요청에서 IllegalArgumentException이 발생해야 합니다.")
                .isTrue();

        executorService.awaitTermination(30, TimeUnit.SECONDS);
        // 시나리오는 맞는데 지금 RestControllerAdvice에서 예외를 처리하고 있어서 register는 정상 흐름으로 끝남
        // 그래서 테스트하기 애매
    }
}