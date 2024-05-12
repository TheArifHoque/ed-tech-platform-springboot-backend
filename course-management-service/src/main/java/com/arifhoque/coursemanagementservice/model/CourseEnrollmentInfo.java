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
@Table(name = "course_enrollement_info_table")
public class CourseEnrollmentInfo {

    @Id
    @GeneratedValue
    @Column(name = "enrollement_id")
    private UUID enrollementId;

    @Column(name = "course_id")
    private UUID courseId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "status")
    private String status;
}
