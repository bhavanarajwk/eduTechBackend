package com.eduTech.eduTech.repository;

import com.eduTech.eduTech.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByClassId(Long classId);
}

