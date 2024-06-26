package com.arifhoque.coursemanagementservice.service;

import com.arifhoque.coursemanagementservice.model.CourseEnrollmentInfo;
import com.arifhoque.coursemanagementservice.repository.CourseEnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CourseEnrollmentService {

    private final CourseEnrollmentRepository courseEnrollmentRepository;

    public CourseEnrollmentService(CourseEnrollmentRepository courseEnrollmentRepository) {
        this.courseEnrollmentRepository = courseEnrollmentRepository;
    }

    public void enrollToCourse(CourseEnrollmentInfo courseEnrollmentInfo) throws Exception {
        if (isAlreadyEnrolled(courseEnrollmentInfo.getUserId(), courseEnrollmentInfo.getCourseId())) {
            throw new Exception("The user is already enrolled in this course!");
        }
        courseEnrollmentRepository.save(courseEnrollmentInfo);
    }

    private boolean isAlreadyEnrolled(UUID userId, UUID courseId) {
        Map<UUID, String> enrolledCourseIdsWithStatusMap = getEnrolledCourseIdsWithStatus(userId);
        return enrolledCourseIdsWithStatusMap.containsKey(courseId);
    }

    public Map<UUID, String> getEnrolledCourseIdsWithStatus(UUID userId) {
        List<CourseEnrollmentInfo> courseEnrollmentInfoList = courseEnrollmentRepository.findByUserId(userId);
        Map<UUID, String> enrolledCourseIdsWithStatusMap = new HashMap<>();
        for (CourseEnrollmentInfo courseEnrollmentInfo : courseEnrollmentInfoList) {
            enrolledCourseIdsWithStatusMap.put(courseEnrollmentInfo.getCourseId(), courseEnrollmentInfo.getStatus());
        }
        return enrolledCourseIdsWithStatusMap;
    }

    public List<UUID> getEnrolledUserIds(UUID courseId) {
        List<CourseEnrollmentInfo> courseEnrollmentInfoList = courseEnrollmentRepository.findByCourseId(courseId);
        return courseEnrollmentInfoList.stream().map(CourseEnrollmentInfo::getUserId).toList();
    }
}
