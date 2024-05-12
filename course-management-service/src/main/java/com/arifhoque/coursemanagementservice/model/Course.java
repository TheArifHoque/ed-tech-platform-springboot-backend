package com.arifhoque.coursemanagementservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "course_table")
public class Course {

    @Id
    @GeneratedValue
    @Column(name = "course_id")
    private UUID courseId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_enrollment_enabled")
    private Boolean isEnrollmentEnabled;

    @Column(name = "course_fee")
    private Integer courseFee;

    @Column(name = "discount")
    private Integer discount;
}
