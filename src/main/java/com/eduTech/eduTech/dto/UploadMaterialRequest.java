package com.eduTech.eduTech.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadMaterialRequest {

    private Long classId;
    private Long subjectId;
    private String materialName;
    private MultipartFile file;
}
