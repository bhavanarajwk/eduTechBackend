package com.eduTech.eduTech.controller;

import com.eduTech.eduTech.entity.StudyMaterial;
import com.eduTech.eduTech.entity.Subject;
import com.eduTech.eduTech.service.SyllabusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/syllabus")
@RequiredArgsConstructor
public class SyllabusController {

    private final SyllabusService syllabusService;

    @PostMapping("/subject")
    public Subject addSubject(@RequestBody Subject subject){
        return syllabusService.addSubject(subject);
    }

    @PostMapping("/material")
    public StudyMaterial uploadMaterial(@RequestBody StudyMaterial material){
        return syllabusService.uploadMaterial(material);
    }

    @GetMapping("/materials/class/{classId}")
    public List<StudyMaterial> getMaterials(@PathVariable Long classId){
        return syllabusService.getMaterialsByClass(classId);
    }

    @PutMapping("/material/{id}")
    public StudyMaterial updateMaterial(@PathVariable Long id,
                                        @RequestBody StudyMaterial material){
        return syllabusService.updateMaterial(id, material);
    }

    @DeleteMapping("/material/{id}")
    public void deleteMaterial(@PathVariable Long id){
        syllabusService.deleteMaterial(id);
    }

    @GetMapping("/search")
    public List<StudyMaterial> searchMaterial(@RequestParam String keyword){
        return syllabusService.searchMaterial(keyword);
    }
}
