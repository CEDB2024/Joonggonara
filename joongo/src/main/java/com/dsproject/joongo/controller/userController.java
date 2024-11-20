package com.dsproject.joongo.controller;

import com.dsproject.joongo.domain.User;
import com.dsproject.joongo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
}
