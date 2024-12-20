package com.dbProject.joongo.service;

import com.dbProject.joongo.domain.Product;
import com.dbProject.joongo.domain.User;
import com.dbProject.joongo.dto.auth.AuthRequest;
import com.dbProject.joongo.global.PasswordUtils;
import com.dbProject.joongo.mapper.ProductMapper;
import com.dbProject.joongo.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final ProductMapper productMapper;

    // 사용자 추가
    public void addUser(AuthRequest.RegisterRequest registerRequest) {
        registerRequest.setUserPassword(PasswordUtils.hashPassword(registerRequest.getUserPassword()));
        User user = registerRequest.toUser();
        userMapper.insertUser(user);
        log.info("userId = {}", user.getUserId());

    }

    // 마지막 insert 사용자 ID 조회
    public Integer getLastInsertId() {
        return userMapper.getLastInsertId();
    }

    public Integer getLastIdInDatabase() {
        return userMapper.getLastIdInDatabase();
    }

    // 사용자 조회 (ID)
    public User getUserById(int userId) {
        return userMapper.selectUserById(userId);
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
    @Transactional
    public boolean deleteUserById(int userId) {
        User deletedUser = userMapper.selectUserById(userId);
        userMapper.updateUser(User.builder()
                .userId(deletedUser.getUserId())
                .userStatus("banned")
                .build());
        // 해당 사용자의 모든 상품 가져오기
        List<Product> products = productMapper.findAllByUserId(userId);

        // 상품 상태를 'reserved'로 업데이트
        products.forEach(product -> {
            productMapper.updateProduct(Product.builder()
                    .productId(product.getProductId())
                    .productStatus("reserved") // 상품 상태를 'reserved'로 변경
                    .build());
        });
        return true;
    }

    // 사용자 인증 (이메일과 비밀번호로 사용자 확인)
    public User authenticate(String email, String userPassword) {
        return userMapper.selectUserByEmailAndPassword(email, userPassword);
    }

    public List<Product> getProductsByUserId(int userId) {
        return userMapper.selectProductsByUserId(userId);
    }

    public Long chargeUserMoney(int userId, int amount) {
        // 현재 금액 가져오기
        User user = userMapper.selectUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // 금액 충전
        long newBalance = user.getMoney() + amount;

        // 데이터베이스 업데이트
        userMapper.updateUser(User.builder()
                .userId(userId)
                .money(newBalance)
                .build());

        return newBalance;
    }

    public String getRoleByUserId(int userId) {
        return userMapper.getRoleByUserId(userId);
    }
}
