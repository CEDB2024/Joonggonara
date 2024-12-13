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
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
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
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getUserProducts(@PathVariable int id) {
        List<Product> products = userService.getProductsByUserId(id);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}/charge")
    public ResponseEntity<?> chargeMoney(@PathVariable int id, @RequestBody Map<String, Integer> requestBody) {
        try {
            int chargeAmount = requestBody.get("amount");
            userService.chargeUserMoney(id, chargeAmount);
            return ResponseEntity.ok("Money charged successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error occurred during money charging.");
        }
    }
}


