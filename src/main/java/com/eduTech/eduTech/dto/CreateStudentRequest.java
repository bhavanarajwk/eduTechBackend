package com.eduTech.eduTech.dto;

import lombok.Data;

@Data
public class CreateStudentRequest {

    private String studentName;
    private String email;
    private String password;
    private String className;
    private String schoolName;
}
