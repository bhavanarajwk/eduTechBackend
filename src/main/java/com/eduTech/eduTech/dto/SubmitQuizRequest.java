package com.eduTech.eduTech.dto;

import lombok.Data;

@Data
public class SubmitQuizRequest {

    private Long studentId;
    private Long questionId;
    private Long selectedOptionId;
}

