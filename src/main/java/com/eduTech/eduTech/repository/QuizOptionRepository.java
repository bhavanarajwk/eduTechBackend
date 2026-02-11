package com.eduTech.eduTech.repository;

import com.eduTech.eduTech.entity.QuizOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizOptionRepository extends JpaRepository<QuizOption, Long> {

    List<QuizOption> findByQuestionId(Long questionId);
}

