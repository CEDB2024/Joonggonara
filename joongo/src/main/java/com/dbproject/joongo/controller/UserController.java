package com.dbproject.joongo.controller;

import com.dbproject.joongo.domain.User;
import com.dbproject.joongo.domain.Product;
import com.dbproject.joongo.service.UserService;
import com.dbproject.joongo.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") // /api를 추가하여 모든 경로 일관성 유지
public class UserController {

    private final UserService userService;
    private final ProductService productService;

    public UserController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    // 사용자 추가
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.addUser(user);
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

    // [추가] 유저의 보유 금액 조회
    @GetMapping("/{id}/money")
    public ResponseEntity<Long> getUserMoney(@PathVariable int id) {
        Long money = userService.getUserMoney(id);
        return ResponseEntity.ok(money);
    }

    // [추가] 내가 등록한 물품 조회
    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getUserProducts(@PathVariable int id) {
        List<Product> products = productService.getProductsByUserId(id);
        return ResponseEntity.ok(products);
    }

    // [추가] 내가 거래한 내역 조회
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<String>> getTransactionHistory(@PathVariable int id) {
        List<String> transactions = userService.getTransactionHistory(id);
        return ResponseEntity.ok(transactions);
    }
}
