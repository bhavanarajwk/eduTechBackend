package com.eduTech.eduTech.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "study_material")

public class StudyMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long classId;
    private Long subjectId;

    private String title;

    private String fileName;
    private String fileType;

    // ⭐ STORE PATH NOT FILE DATA
    private String filePath;

    private LocalDateTime uploadDate;
}
