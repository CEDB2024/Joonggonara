package com.dbproject.joongo.domain;

import lombok.Data;

@Data
public class User {
    private Integer userId;
    private String userName;
    private String nickname;
    private String userPassword;
    private String email;
    private String tel1;
    private String tel2;
    private String location;
    private String userRole;    // ENUM('admin', 'user')
    private String userStatus; // ENUM('active', 'inactive', 'banned')
    private Long money;
}
