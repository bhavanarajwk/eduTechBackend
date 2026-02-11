package com.eduTech.eduTech.service;

import com.eduTech.eduTech.dto.SubmitQuizRequest;
import com.eduTech.eduTech.entity.QuizOption;
import com.eduTech.eduTech.entity.QuizResult;
import com.eduTech.eduTech.repository.QuizOptionRepository;
import com.eduTech.eduTech.repository.QuizResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuizAttemptService {

    private final QuizOptionRepository optionRepository;
    private final QuizResultRepository resultRepository;

    public QuizResult submitAnswer(SubmitQuizRequest request){

        QuizOption option =
                optionRepository.findById(request.getSelectedOptionId())
                        .orElseThrow();

        QuizResult result = new QuizResult();
        result.setStudentId(request.getStudentId());
        result.setQuestionId(request.getQuestionId());
        result.setSelectedOptionId(request.getSelectedOptionId());
        result.setIsCorrect(option.getIsCorrect());
        result.setSubmittedAt(LocalDateTime.now());

        return resultRepository.save(result);
    }
}

