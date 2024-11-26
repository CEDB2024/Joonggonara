package com.dbproject.joongo.service;

import com.dbproject.joongo.domain.User;
import com.dbproject.joongo.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // 사용자 추가
    public void addUser(User user) {
        userMapper.insertUser(user);
    }

    // 사용자 조회 (ID)
    public User getUserById(int userId) {
        return userMapper.selectUserById(userId);
    }

        // UserService.java
    public boolean isEmailRegistered(String email) {
        return userMapper.selectUserByEmail(email) != null;
    }


    // 모든 사용자 조회
    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    }

    // 사용자 수정
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    // 사용자 삭제
    public void deleteUserById(int userId) {
        userMapper.deleteUserById(userId);
    }

    // 사용자 인증 (이메일과 비밀번호로 사용자 확인)
    public User authenticate(String email, String userPassword) {
        return userMapper.selectUserByEmailAndPassword(email, userPassword);
    }
}
