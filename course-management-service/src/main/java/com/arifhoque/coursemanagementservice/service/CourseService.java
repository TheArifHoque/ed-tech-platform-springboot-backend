package com.arifhoque.coursemanagementservice.service;

import com.arifhoque.coursemanagementservice.model.Course;
import com.arifhoque.coursemanagementservice.repository.CourseRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_LIMIT = 10;

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses(Integer pageNumber, Integer limit) {
        pageNumber = Optional.ofNullable(pageNumber).orElse(DEFAULT_PAGE_NUMBER);
        limit = Optional.ofNullable(limit).orElse(DEFAULT_LIMIT);
        PageRequest pageRequest = PageRequest.of(pageNumber, limit);
        return courseRepository.findAllBy(pageRequest);
    }

    public Course getCourseByCourseId(UUID courseId) {
        return courseRepository.findByCourseId(courseId);
    }

    public List<Course> getCourses(List<UUID> courseIds) {
        return courseRepository.findByCourseIdIn(courseIds);
    }

    public void addCourse(Course course) throws Exception {
        Course existingCourse = courseRepository.findByCourseId(course.getCourseId());
        if (existingCourse != null) {
            throw new Exception("Course with id - " + course.getCourseId() + " already exists!");
        }
        courseRepository.save(course);
    }

    public void updateCourse(Course course) throws Exception {
        Course existingCourse = courseRepository.findByCourseId(course.getCourseId());
        if (existingCourse == null) {
            throw new Exception("Course with id - " + course.getCourseId() + " not found!");
        }
        existingCourse.setCourseName(course.getCourseName());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setImageUrl(course.getImageUrl());
        existingCourse.setCourseFee(course.getCourseFee());
        existingCourse.setDiscount(course.getDiscount());
        courseRepository.save(existingCourse);
    }
}
