package com.dbProject.joongo.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {
    private int userId;
    private String userName;
    private String nickname;
    private String email;
    private String phoneNumber; // 여기에서 phonenumber를 가공한 형태로 저장
    private String location;
    private long money;
}
