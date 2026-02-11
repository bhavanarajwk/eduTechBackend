package com.eduTech.eduTech.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="subjects")
@Data
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subjectName;

    private Long classId;
}


