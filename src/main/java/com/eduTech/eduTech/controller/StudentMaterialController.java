package com.eduTech.eduTech.controller;

import com.eduTech.eduTech.entity.StudyMaterial;
import com.eduTech.eduTech.service.StudentMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/materials")
@RequiredArgsConstructor
public class StudentMaterialController {

    private final StudentMaterialService materialService;

    // ✅ Get all materials for logged-in student's class
    @GetMapping("/all")
    public List<StudyMaterial> getAllMaterials(Authentication authentication) {

        String email = authentication.getName();
        return materialService.getAllMaterials(email);
    }

    // ✅ Get materials by subject
    @GetMapping("/subject")
    public List<StudyMaterial> getBySubject(
            Authentication authentication,
            @RequestParam Long subjectId) {

        String email = authentication.getName();
        return materialService.getBySubject(email, subjectId);
    }

    // ✅ Search materials by keyword
    @GetMapping("/search")
    public List<StudyMaterial> search(
            Authentication authentication,
            @RequestParam String keyword) {

        String email = authentication.getName();
        return materialService.search(email, keyword);
    }
}
