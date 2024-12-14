package com.dbProject.joongo.controller;

import com.dbProject.joongo.domain.Product;
import com.dbProject.joongo.domain.User;
import com.dbProject.joongo.dto.auth.AuthRequest;
import com.dbProject.joongo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<?> getUserInfo(@PathVariable int id) {
        try {
            User user = userService.getUserById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            // 필요한 정보만 담아 반환
            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getUserId());
            response.put("userName", user.getUserName());
            response.put("email", user.getEmail());
            response.put("phone", user.getPhoneNumber());
            response.put("location", user.getLocation());
            response.put("money", user.getMoney());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user info");
        }
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

    // 사용자가 등록한 상품 조회
    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getUserProducts(@PathVariable int id) {
        List<Product> products = userService.getProductsByUserId(id);
        return ResponseEntity.ok(products);
    }

    // 금액 충전
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
