package com.arifhoque.userservice.controller;

import com.arifhoque.userservice.model.User;
import com.arifhoque.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.arifhoque.userservice.constant.UserServiceConstant.ROLE_ADMIN;
import static com.arifhoque.userservice.constant.UserServiceConstant.ROLE_USER;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addRegularUser(@RequestBody User user) {
        try {
            userService.validateEmail(user.getEmail());
            userService.validatePassword(user.getPassword());
            user.setRole(ROLE_USER);
            userService.addUser(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/admin")
    public ResponseEntity<User> addAdminUser(@RequestBody User user) {
        try {
            userService.validateEmail(user.getEmail());
            userService.validatePassword(user.getPassword());
            user.setRole(ROLE_ADMIN);
            userService.addUser(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        User user =  userService.getUser(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllRegularUsers() {
        List<UUID> userIds = new ArrayList<>();
        List<User> userList = userService.getListOfUser(userIds);
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAllAdminUsers() {
        List<UUID> userIds = new ArrayList<>();
        List<User> userList = userService.getListOfUser(userIds);
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestBody User user) {
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }
}
