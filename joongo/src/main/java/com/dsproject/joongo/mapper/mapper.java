package com.dsproject.joongo.mapper;

import com.dsproject.joongo.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    // 기존 메서드들
    void insertUser(User user);

    User selectUserById(int userId);

    List<User> selectAllUsers();

    void updateUser(User user);

    void deleteUserById(int userId);

    // 이메일과 비밀번호로 사용자 조회
    User selectUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}

