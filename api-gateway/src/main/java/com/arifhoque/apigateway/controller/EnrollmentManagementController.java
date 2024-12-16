package com.arifhoque.apigateway.controller;

import com.arifhoque.apigateway.service.CourseAPIService;
import com.arifhoque.apigateway.service.CourseEnrollmentAPIService;
import com.arifhoque.commonmodule.model.CustomHttpResponse;
import com.arifhoque.commonmodule.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.arifhoque.commonmodule.constant.CommonConstant.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/enrollment-management-api")
public class EnrollmentManagementController {

    private final CourseEnrollmentAPIService courseEnrollmentAPIService;
    private final CourseAPIService courseAPIService;

    public EnrollmentManagementController(CourseEnrollmentAPIService courseEnrollmentAPIService,
                                          CourseAPIService courseAPIService) {
        this.courseEnrollmentAPIService = courseEnrollmentAPIService;
        this.courseAPIService = courseAPIService;
    }

    @PostMapping
    public ResponseEntity<CustomHttpResponse> enrollToCourse(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                             @RequestBody Map<String, Object> courseEnrollmentInfoMap) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.CREATED, courseEnrollmentAPIService.enrollToCourse(
                courseEnrollmentInfoMap, accessToken));
    }

    @GetMapping("/courses")
    public ResponseEntity<CustomHttpResponse> getEnrolledCourses(@RequestHeader(AUTHORIZATION_HEADER)
                                                                 String accessToken, @RequestParam UUID userId) {

        Map<String, Object> enrollmentAPIResponse = courseEnrollmentAPIService.getAllEnrolledCourseIdsWithStatus(
                userId, accessToken);
        Map<String, Object> enrolledCourseIdsWithStatusMap =
                (Map<String, Object>) enrollmentAPIResponse.get("enrolledCourseIdsWithStatus");
        List<String> enrolledCourseIds = new ArrayList<>(enrolledCourseIdsWithStatusMap.keySet());
        Map<String, Object> courseAPIResponse = courseAPIService.getAllCoursesByIds(enrolledCourseIds);
        List<Map<String, Object>> courseList = (List<Map<String, Object>>) courseAPIResponse.get("courseList");
        for (Map<String, Object> course : courseList) {
            String courseId = course.get("courseId").toString();
            if (enrolledCourseIdsWithStatusMap.containsKey(courseId)) {
                course.put("status", enrolledCourseIdsWithStatusMap.get(courseId));
            }
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, Map.of("courseList", courseList));
    }
}
