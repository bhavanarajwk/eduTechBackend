package com.eduTech.eduTech.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="materials")
@Data
public class StudyMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String fileUrl;
    private String fileType;

    private Long subjectId;
    private Long classId;

    private LocalDateTime uploadDate;
}

