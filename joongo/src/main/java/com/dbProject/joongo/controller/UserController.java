package com.dbProject.joongo.controller;

import com.dbProject.joongo.domain.Product;
import com.dbProject.joongo.domain.User;
import com.dbProject.joongo.dto.auth.AuthRequest;
import com.dbProject.joongo.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    // 사용자 추가
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody AuthRequest.RegisterRequest registerRequest) {
        userService.addUser(registerRequest);
        return ResponseEntity.ok("User created successfully!");
    }

    // ID로 사용자 조회
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email) {
        User user = userService.getUserByEmail(email);

        return ResponseEntity.ok(user);
    }

    // 모든 사용자 조회
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 사용자 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody User user) {
        user.setUserId(id);
        userService.updateUser(user);
        return ResponseEntity.ok("User updated successfully!");
    }

    // 사용자 삭제
    @PostMapping("/{userId}/delete")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("userId") int userId) {
        boolean result = userService.deleteUserById(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{userId}/products")
    public ResponseEntity<List<Product>> getUserProducts(@PathVariable("userId") int userId) {
        List<Product> products = userService.getProductsByUserId(userId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/{userId}/charge")
    public ResponseEntity<Long> chargeMoney(@PathVariable("userId") int userId,
                                            @RequestBody Map<String, Integer> requestBody) {
        int chargeAmount = requestBody.get("amount");

        return ResponseEntity.ok(userService.chargeUserMoney(userId, chargeAmount));
    }

    @GetMapping("{userId}/role")
    public ResponseEntity<String> getRoleByUserId(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(userService.getRoleByUserId(userId));
    }
}


