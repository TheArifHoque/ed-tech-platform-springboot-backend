package com.arifhoque.apigateway.controller;

import com.arifhoque.apigateway.service.UserAPIService;
import com.arifhoque.commonmodule.model.CustomHttpResponse;
import com.arifhoque.commonmodule.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

import static com.arifhoque.commonmodule.constant.CommonConstant.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/profile-page-api")
public class ProfilePageController {

    private final UserAPIService userAPIService;

    public ProfilePageController(UserAPIService userAPIService) {
        this.userAPIService = userAPIService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CustomHttpResponse> getUserData(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                          @PathVariable UUID userId) {
        Map<String, Object> user;
        try {
            user = userAPIService.getUserById(userId, accessToken);
        } catch (Exception e) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.NOT_FOUND, "404",
                    "Failed to fetch user data! Reason: " + e.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, Map.of("user", user));
    }
}
