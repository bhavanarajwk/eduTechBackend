package com.eduTech.eduTech.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateQuizRequest {

    private String questionText;

    private Long classId;

    private Long subjectId;

    private List<OptionDto> options;
}
