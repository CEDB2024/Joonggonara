package com.dbProject.joongo;

import com.dbProject.joongo.controller.AuthenticationController;
import com.dbProject.joongo.dto.auth.AuthRequest;
import com.dbProject.joongo.service.AuthService;
import com.dbProject.joongo.service.UserService;
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
    @Autowired
    private UserService userService;

    @Test
    public void 회원가입_기존_아이디_검증_테스트() throws InterruptedException, ExecutionException {
        // given
        int threadNum = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);

        AuthRequest.RegisterRequest newMember1 = new AuthRequest.RegisterRequest(
                "randomUser1", "nickname1", "password1", "random1@example.com",
                "1234", "1234", "Seoul", "USER", "ACTIVE", 5000L
        );
        AuthRequest.RegisterRequest newMember2 = new AuthRequest.RegisterRequest(
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

        // then
        submit1.get();
        submit2.get();

        int afterTest = userService.getLastInsertId();
        int maxId = userService.getLastIdInDatabase();
        Assertions.assertThat(afterTest).isEqualTo(maxId);

        executorService.awaitTermination(30, TimeUnit.SECONDS);
        // 시나리오는 맞는데 지금 RestControllerAdvice에서 예외를 처리하고 있어서 register는 정상 흐름으로 끝남
        // 그래서 테스트하기 애매
        // 로직만 테스트 해야되는데, 컨트롤러를 테스트함 , 서블릿 거쳐가야해서 하기 빡셈
        // 지금 테스트도 뭔가 이상함 테스트로 생성한 멤버를 지우지 않고있음..
        // 지우게 할 수 있는데 그러면 실패 시에도 지워짐..
    }
}