package com.eduTech.eduTech.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_results")
@Data
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private Long questionId;
    private Long selectedOptionId;

    private Boolean isCorrect;

    private LocalDateTime submittedAt;
}

