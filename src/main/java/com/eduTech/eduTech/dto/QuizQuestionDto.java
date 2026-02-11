package com.eduTech.eduTech.dto;

import com.eduTech.eduTech.entity.QuizOption;
import lombok.Data;

import java.util.List;

@Data
public class QuizQuestionDto {
    private Long id;
    private String questionText;
    private List<QuizOption> options;
}
