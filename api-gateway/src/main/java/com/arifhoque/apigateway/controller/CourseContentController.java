package com.arifhoque.apigateway.controller;

import com.arifhoque.apigateway.service.CourseContentAPIService;
import com.arifhoque.commonmodule.model.CustomHttpResponse;
import com.arifhoque.commonmodule.util.ResponseBuilder;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.arifhoque.commonmodule.constant.CommonConstant.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/course-content-api")
public class CourseContentController {

    private final CourseContentAPIService courseContentAPIService;

    public CourseContentController(CourseContentAPIService courseContentAPIService) {
        this.courseContentAPIService = courseContentAPIService;
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CustomHttpResponse> getCourseContents(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                                @PathVariable UUID courseId,
                                                                @RequestParam @Nullable Integer pageNumber,
                                                                @RequestParam @Nullable Integer limit) {
        List<Map<String, Object>> courseContents;
        try {
            courseContents = courseContentAPIService.getCourseContents(courseId, pageNumber, limit, accessToken);
        } catch (Exception e) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.BAD_REQUEST, "400",
                    "Failed to fetch course contents! Reason: " + e.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, Map.of("courseContents", courseContents));
    }
}
