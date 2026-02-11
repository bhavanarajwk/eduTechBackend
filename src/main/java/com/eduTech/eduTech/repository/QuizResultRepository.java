package com.eduTech.eduTech.repository;

import com.eduTech.eduTech.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    List<QuizResult> findByStudentId(Long studentId);
}
