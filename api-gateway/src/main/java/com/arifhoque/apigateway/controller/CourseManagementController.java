package com.arifhoque.apigateway.controller;

import com.arifhoque.apigateway.service.CourseAPIService;
import com.arifhoque.commonmodule.model.CustomHttpResponse;
import com.arifhoque.commonmodule.util.ResponseBuilder;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

import static com.arifhoque.commonmodule.constant.CommonConstant.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/course-management-api")
public class CourseManagementController {

    private final CourseAPIService courseAPIService;

    public CourseManagementController(CourseAPIService courseAPIService) {
        this.courseAPIService = courseAPIService;
    }

    @PostMapping
    public ResponseEntity<CustomHttpResponse> addCourse(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                        @RequestBody Map<String, Object> course) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.CREATED, courseAPIService.addNewCourse(course,
                accessToken));
    }

    @GetMapping
    public ResponseEntity<CustomHttpResponse> getAllCourses(@RequestParam @Nullable Integer pageNumber,
                                                            @RequestParam @Nullable Integer limit) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, courseAPIService.getAllCourses(pageNumber, limit));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CustomHttpResponse> getCourseById(@PathVariable UUID courseId) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, courseAPIService.getCourseById(courseId));
    }

    @PostMapping("/update")
    public ResponseEntity<CustomHttpResponse> updateCourse(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                           @RequestBody Map<String, Object> course) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, courseAPIService.updateCourse(course,
                accessToken));
    }

    @GetMapping("/enrollment/{userId}")
    public ResponseEntity<CustomHttpResponse> getAllEnrolledCourses(@RequestHeader(AUTHORIZATION_HEADER)
                                                                    String accessToken, @PathVariable UUID userId) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, courseAPIService.getAllEnrolledCourses(userId,
                accessToken));
    }

    @GetMapping("/enrollment/{courseId}/{userId}")
    public ResponseEntity<CustomHttpResponse> getEnrollmentStatus(@RequestHeader(AUTHORIZATION_HEADER)
                                                                  String accessToken, @PathVariable UUID courseId,
                                                                  @PathVariable UUID userId) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, courseAPIService.getEnrollmentStatus(courseId,
                userId, accessToken));
    }
}
