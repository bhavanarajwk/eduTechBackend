package com.eduTech.eduTech.repository;

import com.eduTech.eduTech.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    List<QuizQuestion> findByClassId(Long classId);

    List<QuizQuestion> findByQuestionTextContainingIgnoreCase(String keyword);
}
