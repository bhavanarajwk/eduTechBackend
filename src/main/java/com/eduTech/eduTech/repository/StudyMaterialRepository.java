package com.eduTech.eduTech.repository;

import com.eduTech.eduTech.entity.StudyMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long> {

    List<StudyMaterial> findByClassId(Long classId);

    List<StudyMaterial> findByTitleContainingIgnoreCase(String keyword);
}
