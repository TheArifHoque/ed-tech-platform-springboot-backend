package com.arifhoque.coursemanagementservice.repository;

import com.arifhoque.coursemanagementservice.model.CourseEnrollmentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollmentInfo, UUID> {

    List<CourseEnrollmentInfo> findByCourseId(UUID courseId);

    List<CourseEnrollmentInfo> findByUserId(UUID userId);

    CourseEnrollmentInfo findByCourseIdAndUserId(UUID courseId, UUID userId);
}
