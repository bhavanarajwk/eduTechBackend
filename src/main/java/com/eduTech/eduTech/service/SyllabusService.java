package com.eduTech.eduTech.service;

import com.eduTech.eduTech.entity.StudyMaterial;
import com.eduTech.eduTech.entity.Subject;
import com.eduTech.eduTech.repository.StudyMaterialRepository;
import com.eduTech.eduTech.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyllabusService {

    private final SubjectRepository subjectRepository;
    private final StudyMaterialRepository materialRepository;

    public Subject addSubject(Subject subject){
        return subjectRepository.save(subject);
    }

    public StudyMaterial uploadMaterial(StudyMaterial material){
        material.setUploadDate(LocalDateTime.now());
        return materialRepository.save(material);
    }

    public List<StudyMaterial> getMaterialsByClass(Long classId){
        return materialRepository.findByClassId(classId);
    }

    public StudyMaterial updateMaterial(Long id, StudyMaterial material){
        material.setId(id);
        return materialRepository.save(material);
    }

    public void deleteMaterial(Long id){
        materialRepository.deleteById(id);
    }

    public List<StudyMaterial> searchMaterial(String keyword){
        return materialRepository.findByTitleContaining(keyword);
    }
}

