package com.eduTech.eduTech.repository;

import com.eduTech.eduTech.entity.StudyMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long> {

    List<StudyMaterial> findByClassId(Long classId);

    List<StudyMaterial> findByClassIdAndSubjectId(Long classId, Long subjectId);

    List<StudyMaterial> findByTitleContainingIgnoreCase(String keyword);

    @Query("""
        SELECT m FROM StudyMaterial m
        WHERE m.classId = :classId
        AND LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<StudyMaterial> searchInClass(Long classId, String keyword);
}
