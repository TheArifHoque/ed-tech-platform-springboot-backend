package com.arifhoque.apigateway.controller;

import com.arifhoque.apigateway.service.UserAPIService;
import com.arifhoque.commonmodule.model.CustomHttpResponse;
import com.arifhoque.commonmodule.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

import static com.arifhoque.commonmodule.constant.CommonConstant.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/user-management-api")
public class UserManagementController {

    private final UserAPIService userAPIService;

    public UserManagementController(UserAPIService userAPIService) {
        this.userAPIService = userAPIService;
    }

    @PostMapping
    public ResponseEntity<CustomHttpResponse> addRegularUser(@RequestBody Map<String, Object> userData) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.CREATED, userAPIService.addRegularUser(userData));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CustomHttpResponse> getUserData(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                          @PathVariable UUID userId) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, userAPIService.getUserById(userId, accessToken));
    }

    @PostMapping("/profile")
    public ResponseEntity<CustomHttpResponse> updateProfile(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                            @RequestBody Map<String, Object> user) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, userAPIService.updateUserData(user, accessToken));
    }

    @PostMapping("/image")
    public ResponseEntity<CustomHttpResponse> updatePhoto(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                          @RequestBody Map<String, String> imageUrlMap) {
        String userId = imageUrlMap.get("userId");
        String imageUrl = imageUrlMap.get("imageUrl");
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, userAPIService.updateUserPhoto(userId, imageUrl,
                accessToken));
    }

    @PostMapping("/password")
    public ResponseEntity<CustomHttpResponse> updatePassword(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                             @RequestBody Map<String, String> passwordMap) {
        String userId = passwordMap.get("userId");
        String password = passwordMap.get("password");
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, userAPIService.updatePassword(userId, password,
                accessToken));
    }
}
