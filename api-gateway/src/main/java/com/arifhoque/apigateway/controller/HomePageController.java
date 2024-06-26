package com.arifhoque.apigateway.controller;

import com.arifhoque.apigateway.service.CourseAPIService;
import com.arifhoque.commonmodule.model.CustomHttpResponse;
import com.arifhoque.commonmodule.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home-page-api")
public class HomePageController {

    private final CourseAPIService courseAPIService;

    public HomePageController(CourseAPIService courseAPIService) {
        this.courseAPIService = courseAPIService;
    }

    @GetMapping
    public ResponseEntity<CustomHttpResponse> getHomeScreenData() {
        List<Map<String, Object>> courseList;
        try {
            courseList = courseAPIService.getAllCourses(0, 6);
        } catch (Exception e) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.BAD_REQUEST, "400",
                    "Failed to fetch course list! Reason: " + e.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, Map.of("courseList", courseList));
    }
}
