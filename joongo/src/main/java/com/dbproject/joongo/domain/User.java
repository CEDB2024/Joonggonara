package com.dbproject.joongo.domain;

import lombok.Data;

@Data
public class User {
    private int userId;       // 자동 증가, 생성 시 필요 없음
    private String userName;
    private String nickname;
    private String userPassword;
    private String email;
    private String tel_1;
    private String tel_2;
    private String location;
    private String userRole;     // 기본값
    private String userStatus; // 기본값
    private long money;             // 기본값

    // Getters and Setters
}
