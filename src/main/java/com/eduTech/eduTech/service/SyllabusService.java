package com.eduTech.eduTech.service;

import com.eduTech.eduTech.dto.CreateSubjectRequest;
import com.eduTech.eduTech.dto.UploadMaterialRequest;
import com.eduTech.eduTech.entity.StudyMaterial;
import com.eduTech.eduTech.entity.Subject;
import com.eduTech.eduTech.repository.StudyMaterialRepository;
import com.eduTech.eduTech.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyllabusService {

    private final SubjectRepository subjectRepository;
    private final StudyMaterialRepository materialRepository;

    // ✅ ADD SUBJECT USING DTO
    public Subject addSubject(CreateSubjectRequest request){

        Subject subject = new Subject();
        subject.setClassId(request.getClassId());
        subject.setSubjectName(request.getSubjectName());

        return subjectRepository.save(subject);
    }

    // ✅ GET SUBJECTS BY CLASS
    public List<Subject> getSubjectsByClass(Long classId){
        return subjectRepository.findByClassId(classId);
    }

    // ✅ UPLOAD MATERIAL WITH FILE
    public StudyMaterial uploadMaterial(UploadMaterialRequest request){

        StudyMaterial material = new StudyMaterial();

        material.setClassId(request.getClassId());
        material.setSubjectId(request.getSubjectId());
        material.setTitle(request.getMaterialName());
        material.setUploadDate(LocalDateTime.now());

        try {
            material.setFileName(request.getFile().getOriginalFilename());
            material.setFileType(request.getFile().getContentType());
            material.setFileData(request.getFile().getBytes());
        } catch (IOException e) {
            throw new RuntimeException("File upload failed");
        }

        return materialRepository.save(material);
    }

    // ✅ GET MATERIALS BY CLASS
    public List<StudyMaterial> getMaterialsByClass(Long classId){
        return materialRepository.findByClassId(classId);
    }

    // ✅ UPDATE MATERIAL
    public StudyMaterial updateMaterial(Long id, UploadMaterialRequest request){

        StudyMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        material.setClassId(request.getClassId());
        material.setSubjectId(request.getSubjectId());
        material.setTitle(request.getMaterialName());

        if(request.getFile() != null && !request.getFile().isEmpty()){
            try {
                material.setFileName(request.getFile().getOriginalFilename());
                material.setFileType(request.getFile().getContentType());
                material.setFileData(request.getFile().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("File update failed");
            }
        }

        return materialRepository.save(material);
    }

    // ✅ DELETE MATERIAL
    public void deleteMaterial(Long id){
        materialRepository.deleteById(id);
    }

    // ✅ SEARCH MATERIAL
    public List<StudyMaterial> searchMaterial(String keyword){
        return materialRepository.findByTitleContainingIgnoreCase(keyword);
    }
}
