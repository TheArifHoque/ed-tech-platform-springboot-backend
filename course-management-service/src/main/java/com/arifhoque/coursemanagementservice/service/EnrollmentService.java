package com.arifhoque.coursemanagementservice.service;

import com.arifhoque.coursemanagementservice.model.Course;
import com.arifhoque.coursemanagementservice.model.EnrollmentInfo;
import com.arifhoque.coursemanagementservice.repository.EnrollmentInfoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EnrollmentService {

    private final EnrollmentInfoRepository enrollmentRepository;
    private final CourseService courseService;

    public EnrollmentService(EnrollmentInfoRepository enrollmentRepository, CourseService courseService) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseService = courseService;
    }

    public void enrollToCourse(UUID courseId, UUID userId, String status) {
        EnrollmentInfo existingEnrollmentInfo = enrollmentRepository.findByCourseIdAndUserId(courseId, userId);
        if (existingEnrollmentInfo == null) {
            existingEnrollmentInfo = new EnrollmentInfo();
            existingEnrollmentInfo.setCourseId(courseId);
            existingEnrollmentInfo.setUserId(userId);
        }
        existingEnrollmentInfo.setStatus(status);
        enrollmentRepository.save(existingEnrollmentInfo);
    }

    public List<Course> getEnrolledCourses(UUID userId) {
        List<EnrollmentInfo> enrollmentInfoList = enrollmentRepository.findByUserId(userId);
        Map<UUID, String> enrolledCourseIdsWithStatusMap = new HashMap<>();
        for (EnrollmentInfo enrollmentInfo : enrollmentInfoList) {
            enrolledCourseIdsWithStatusMap.put(enrollmentInfo.getCourseId(), enrollmentInfo.getStatus());
        }
        List<UUID> enrolledCourseIds = new ArrayList<>(enrolledCourseIdsWithStatusMap.keySet());
        List<Course> enrolledCourses = courseService.getListOfCourse(enrolledCourseIds);
        for (Course enrolledCourse : enrolledCourses) {
            enrolledCourse.setEnrollmentStatus(enrolledCourseIdsWithStatusMap.get(enrolledCourse.getCourseId()));
        }
        return enrolledCourses;
    }

    public String getEnrollmentStatus(UUID courseId, UUID userId) throws Exception {
        EnrollmentInfo existingEnrollmentInfo = enrollmentRepository.findByCourseIdAndUserId(courseId, userId);
        if (existingEnrollmentInfo == null) {
            throw new Exception("No enrollment info found for user id - " + userId + " and course id - " + courseId);
        }
        return existingEnrollmentInfo.getStatus();
    }

    public List<UUID> getEnrolledUserIds(UUID courseId) {
        List<EnrollmentInfo> courseEnrollmentInfoList = enrollmentRepository.findByCourseId(courseId);
        return courseEnrollmentInfoList.stream().map(EnrollmentInfo::getUserId).toList();
    }
}
