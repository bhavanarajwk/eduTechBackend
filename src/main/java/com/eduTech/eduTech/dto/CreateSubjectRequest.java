package com.eduTech.eduTech.dto;

import lombok.Data;

@Data
public class CreateSubjectRequest {

    private Long classId;
    private String subjectName;
}
