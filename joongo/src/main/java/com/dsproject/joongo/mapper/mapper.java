package com.dsproject.joongo.mapper;

import com.dsproject.joongo.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    // 사용자 추가
    void insertUser(User user);

    // 사용자 조회 (ID)
    User selectUserById(int userId);

    // 모든 사용자 조회
    List<User> selectAllUsers();

    // 사용자 수정
    void updateUser(User user);

    // 사용자 삭제
    void deleteUserById(int userId);
}
