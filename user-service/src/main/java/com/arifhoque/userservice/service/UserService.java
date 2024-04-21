package com.arifhoque.userservice.service;

import com.arifhoque.userservice.model.User;
import com.arifhoque.userservice.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getUser(UUID userId) {
        return userRepo.findById(userId).orElse(null);
    }

    public List<User> getListOfUser(List<UUID> userIds) {
        return userRepo.findByUserIdIn(userIds);
    }

    public void addUser(User user) throws Exception {
        User existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new Exception("User with email id - " + user.getEmail() + " already exists!");
        }
        userRepo.save(user);
    }

    public void updateUser(User user) throws Exception {
        User existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser == null) {
            throw new Exception("User with email id - " + user.getEmail() + " does not exists!");
        }
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setGender(user.getGender());
        existingUser.setDateOfBirth(user.getDateOfBirth());
        userRepo.save(existingUser);
    }
}
