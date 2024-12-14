package com.dbProject.joongo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private int userId;
    private String userName;
    private String nickname;
    private String userPassword;
    private String email;
    private String tel_1;
    private String tel_2;
    private String location;
    private String userRole;
    private String userStatus;
    private long money;
    private String phoneNumber;

}
