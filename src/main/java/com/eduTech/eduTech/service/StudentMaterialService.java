package com.eduTech.eduTech.service;

import com.eduTech.eduTech.entity.ClassEntity;
import com.eduTech.eduTech.entity.StudyMaterial;
import com.eduTech.eduTech.entity.User;
import com.eduTech.eduTech.repository.ClassRepository;
import com.eduTech.eduTech.repository.StudyMaterialRepository;
import com.eduTech.eduTech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentMaterialService {

    private final UserRepository userRepository;
    private final StudyMaterialRepository materialRepository;
    private final ClassRepository classRepository;

    // ✅ GET ALL MATERIALS
    public List<StudyMaterial> getAllMaterials(String email){

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(student.getClassName() == null){
            throw new RuntimeException("Student class is not assigned");
        }

        ClassEntity classEntity = classRepository
                .findByClassName(student.getClassName())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        return materialRepository.findByClassId(classEntity.getId());
    }


    // ✅ GET MATERIALS BY SUBJECT
    public List<StudyMaterial> getBySubject(String email, Long subjectId){

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if(student.getStudentClass() == null){
            throw new RuntimeException("Student class is not assigned");
        }

        ClassEntity classEntity = classRepository
                .findByClassName(student.getStudentClass())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        return materialRepository
                .findByClassIdAndSubjectId(classEntity.getId(), subjectId);
    }


    // ✅ SEARCH MATERIALS
    public List<StudyMaterial> search(String email, String keyword){

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if(student.getStudentClass() == null){
            throw new RuntimeException("Student class is not assigned");
        }

        ClassEntity classEntity = classRepository
                .findByClassName(student.getStudentClass())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        return materialRepository.searchInClass(classEntity.getId(), keyword);
    }
}
