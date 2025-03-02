package com.arifhoque.coursemanagementservice.controller;

import com.arifhoque.commonmodule.model.CustomHttpResponse;
import com.arifhoque.commonmodule.util.ResponseBuilder;
import com.arifhoque.coursemanagementservice.model.CourseContent;
import com.arifhoque.coursemanagementservice.service.CourseContentService;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.arifhoque.commonmodule.constant.CommonConstant.MESSAGE;

@RestController
@RequestMapping("/course-content")
public class CourseContentController {

    private final CourseContentService courseContentService;

    public CourseContentController(CourseContentService courseContentService) {
        this.courseContentService = courseContentService;
    }

    @GetMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<CustomHttpResponse> getCourseContents(@PathVariable UUID courseId,
                                                                @RequestParam @Nullable Integer pageNumber,
                                                                @RequestParam @Nullable Integer limit) {
        List<CourseContent> courseContents;
        try {
            courseContents = courseContentService.getAllCourseContentsByCourseId(courseId, pageNumber, limit);
        } catch (Exception ex) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.BAD_REQUEST, "400",
                    "Failed to fetch course contents! Reason: " + ex.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, Map.of("courseContents", courseContents));
    }

    @GetMapping("/{courseId}/preview")
    public ResponseEntity<CustomHttpResponse> getCourseContentsPreview(@PathVariable UUID courseId,
                                                                       @RequestParam @Nullable Integer pageNumber,
                                                                       @RequestParam @Nullable Integer limit) {
        List<CourseContent> courseContentsPreview;
        try {
            courseContentsPreview = courseContentService.getAllCourseContentsPreviewByCourseId(courseId,
                    pageNumber, limit);
        } catch (Exception ex) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.BAD_REQUEST, "400",
                    "Failed to fetch course contents preview! Reason: " + ex.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, Map.of("courseContentsPreview",
                courseContentsPreview));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomHttpResponse> addCourseContent(@RequestBody CourseContent courseContent) {
        try {
            courseContentService.addCourseContent(courseContent);
        } catch (Exception ex) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.BAD_REQUEST, "400",
                    "Failed to add course content! Reason: " + ex.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.CREATED, Map.of(MESSAGE,
                "Successfully added course content"));
    }

    @DeleteMapping("/{contentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomHttpResponse> deleteCourseContent(@PathVariable UUID contentId) {
        try {
            courseContentService.deleteCourseContent(contentId);
        } catch (Exception ex) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.EXPECTATION_FAILED, "417",
                    "Failed to delete course content! Reason: " + ex.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.CREATED, Map.of(MESSAGE,
                "Successfully deleted course content"));
    }
}
