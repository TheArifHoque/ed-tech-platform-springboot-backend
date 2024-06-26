package com.arifhoque.apigateway.controller;

import com.arifhoque.apigateway.service.UserAPIService;
import com.arifhoque.commonmodule.model.CustomHttpResponse;
import com.arifhoque.commonmodule.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user-management-api")
public class UserManagementController {

    private final UserAPIService userAPIService;

    public UserManagementController(UserAPIService userAPIService) {
        this.userAPIService = userAPIService;
    }

    @PostMapping
    public ResponseEntity<CustomHttpResponse> addRegularUser(@RequestBody Map<String, Object> userData) {
        String message;
        try {
            message = userAPIService.registerRegularUser(userData);
        } catch (Exception ex) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.BAD_REQUEST, "400",
                    "Failed to add user! Reason: " + ex.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, Map.of("message", message));
    }
}
