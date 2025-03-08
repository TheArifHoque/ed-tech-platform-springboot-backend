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

    @PostMapping("/register")
    public ResponseEntity<CustomHttpResponse> addRegularUser(@RequestBody Map<String, Object> userData) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.CREATED, userAPIService.addRegularUser(userData));
    }

    @PostMapping("/init")
    public ResponseEntity<CustomHttpResponse> initUser(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                       @RequestBody Map<String, Object> userData) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.CREATED, userAPIService.initUser(userData, accessToken));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CustomHttpResponse> getUserById(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                          @PathVariable UUID userId) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, userAPIService.getUserById(userId, accessToken));
    }

    @GetMapping
    public ResponseEntity<CustomHttpResponse> getAllRegularUser(@RequestHeader(AUTHORIZATION_HEADER) String accessToken) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, userAPIService.getAllRegularUser(accessToken));
    }

    @PostMapping("/profile")
    public ResponseEntity<CustomHttpResponse> updateProfile(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                            @RequestBody Map<String, Object> user) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, userAPIService.updateUserData(user, accessToken));
    }

    @PostMapping("/image")
    public ResponseEntity<CustomHttpResponse> updateImageUrl(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                             @RequestBody Map<String, Object> imageUrlMap) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, userAPIService.updateImageUrl(imageUrlMap,
                accessToken));
    }

    @PostMapping("/password")
    public ResponseEntity<CustomHttpResponse> updatePassword(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                             @RequestBody Map<String, Object> passwordMap) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, userAPIService.updatePassword(passwordMap,
                accessToken));
    }
}
