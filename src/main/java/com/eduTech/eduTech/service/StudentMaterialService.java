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

    public List<StudyMaterial> getAllMaterials(String email){

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        ClassEntity classEntity = classRepository
                .findByClassName(student.getStudentClass());

        if(classEntity == null){
            throw new RuntimeException("Class not found");
        }

        Long classId = classEntity.getId();

        return materialRepository.findByClassId(classId);
    }

    public List<StudyMaterial> getBySubject(String email, Long subjectId){

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        ClassEntity classEntity = classRepository
                .findByClassName(student.getStudentClass());

        if(classEntity == null){
            throw new RuntimeException("Class not found");
        }

        Long classId = classEntity.getId();

        return materialRepository.findByClassIdAndSubjectId(classId, subjectId);
    }

    public List<StudyMaterial> search(String email, String keyword){

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        ClassEntity classEntity = classRepository
                .findByClassName(student.getStudentClass());

        if(classEntity == null){
            throw new RuntimeException("Class not found");
        }

        Long classId = classEntity.getId();

        return materialRepository.searchInClass(classId, keyword);
    }
}
