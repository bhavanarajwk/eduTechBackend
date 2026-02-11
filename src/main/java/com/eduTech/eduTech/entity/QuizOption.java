package com.eduTech.eduTech.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "quiz_options")
@Data
public class QuizOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long questionId;

    private String optionText;

    private Boolean isCorrect;
}
