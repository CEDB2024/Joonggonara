package com.dbProject.joongo.dto.auth;

import com.dbProject.joongo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthRequest {

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        private String userName;
        private String nickname;
        private String userPassword;
        private String email;
        private String tel_1;
        private String tel_2;
        private String location;


        public User toUser() {
            return User.builder()
                    .userName(this.userName)
                    .nickname(this.nickname)
                    .userPassword(this.userPassword) // 비밀번호 해싱
                    .email(this.email)
                    .tel_1(this.tel_1)
                    .tel_2(this.tel_2)
                    .location(this.location)
                    .build();
        }
    }
}
