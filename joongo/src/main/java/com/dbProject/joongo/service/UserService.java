package com.dbProject.joongo.service;

import com.dbProject.joongo.domain.User;
import com.dbProject.joongo.dto.auth.AuthRequest;
import com.dbProject.joongo.global.PasswordUtils;
import com.dbProject.joongo.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    // 사용자 추가
    public void addUser(AuthRequest.RegisterRequest registerRequest) {
        registerRequest.setUserPassword(PasswordUtils.hashPassword(registerRequest.getUserPassword()));
        User user = registerRequest.toUser();
        userMapper.insertUser(user);
    }

    // 사용자 조회 (ID)
    public User getUserById(int userId) {return userMapper.selectUserById(userId);
    }

    public boolean isEmailRegistered(String email) {
        return userMapper.selectUserByEmail(email) != null;
    }

    public User getUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
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
