package com.eduTech.eduTech.controller;

import com.eduTech.eduTech.dto.CreateSubjectRequest;
import com.eduTech.eduTech.dto.UploadMaterialRequest;
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

    // ✅ ADD SUBJECT
    @PostMapping("/subject")
    public Subject addSubject(@RequestBody CreateSubjectRequest request){
        return syllabusService.addSubject(request);
    }

    // ✅ GET SUBJECTS BY CLASS
    @GetMapping("/subjects/class/{classId}")
    public List<Subject> getSubjectsByClass(@PathVariable Long classId){
        return syllabusService.getSubjectsByClass(classId);
    }

    //  UPLOAD MATERIAL (FILE UPLOAD)
    @PostMapping("/material/upload")
    public StudyMaterial uploadMaterial(
            @ModelAttribute UploadMaterialRequest request) {

        return syllabusService.uploadMaterial(request);
    }


    // ✅ GET MATERIALS BY CLASS
    @GetMapping("/materials/class/{classId}") //based on class id
    public List<StudyMaterial> getMaterials(@PathVariable Long classId){
        return syllabusService.getMaterialsByClass(classId);
    }

    // ✅ UPDATE MATERIAL
    @PutMapping("/material/{id}")
    public StudyMaterial updateMaterial(
            @PathVariable Long id,
            @ModelAttribute UploadMaterialRequest request){
        return syllabusService.updateMaterial(id, request);
    }


    // ✅ DELETE MATERIAL
    @DeleteMapping("/material/{id}") //api not working
    public void deleteMaterial(@PathVariable Long id){
        syllabusService.deleteMaterial(id);
    }

    // ✅ SEARCH MATERIAL
    @GetMapping("/search") //api not working here
    public List<StudyMaterial> searchMaterial(@RequestParam String keyword){
        return syllabusService.searchMaterial(keyword);
    }
}
