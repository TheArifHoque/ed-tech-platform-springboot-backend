package com.arifhoque.userservice.service;

import com.arifhoque.userservice.model.User;
import com.arifhoque.userservice.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.arifhoque.userservice.constant.UserServiceConstant.VALID_EMAIL_REGEX;
import static com.arifhoque.userservice.constant.UserServiceConstant.VALID_PASSWORD_REGEX;

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

    public void updateProfileImage(UUID userId, String imageUrl) throws Exception {
        User existingUser = userRepo.findById(userId).orElse(null);
        if (existingUser == null) {
            throw new Exception("User with id - " + userId + " does not exists!");
        }
        existingUser.setImageUrl(imageUrl);
        userRepo.save(existingUser);
    }

    public void validateEmail(String email) throws Exception {
        boolean isEmailValid = isEmailValid(email);
        if (!isEmailValid) {
            throw new Exception("Email id - " + email + " is not valid!");
        }
    }

    private boolean isEmailValid(String email) {
        if (!StringUtils.hasLength(email))
            return false;
        return Pattern.compile(VALID_EMAIL_REGEX).matcher(email).matches();
    }

    public void validatePassword(String password) throws Exception {
        boolean isPasswordValid = isPasswordValid(password);
        if (!isPasswordValid) {
            throw new Exception("Password should be minimum eight characters. and contain at least one uppercase, " +
                    "one lowercase, one digit, one special character.");
        }
    }

    private boolean isPasswordValid(String password) {
        if (!StringUtils.hasLength(password))
            return false;
        return Pattern.compile(VALID_PASSWORD_REGEX).matcher(password).matches();
    }
}
