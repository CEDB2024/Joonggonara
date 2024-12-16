package com.dbProject.joongo.global.exception.advice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.dbProject.joongo.global.exception.ErrorReason;
import javax.security.auth.login.LoginException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice {

    //Enum 안뽑음..
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorReason illegalExHandler(IllegalArgumentException ex) {
        return new ErrorReason("ARG400", ex.getMessage(), false);
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(Exception.class)
    public ErrorReason generalExceptionHandler(Exception ex) {
        return new ErrorReason("SER500", "서버 오류", false);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(LoginException.class)
    public ErrorReason loginExceptionHandler(Exception ex) {
        return new ErrorReason(ex.getMessage(), "서버 오류", false);
    }


}
