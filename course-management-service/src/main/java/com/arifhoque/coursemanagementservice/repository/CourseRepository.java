package com.arifhoque.coursemanagementservice.repository;

import com.arifhoque.coursemanagementservice.model.Course;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    List<Course> findAllBy(PageRequest pageRequest);

    Course findByCourseId(UUID courseId);

    List<Course> findByCourseIdIn(List<UUID> courseIds);
}
