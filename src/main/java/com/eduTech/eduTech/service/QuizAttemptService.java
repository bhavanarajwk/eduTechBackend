package com.eduTech.eduTech.service;

import com.eduTech.eduTech.dto.QuizQuestionDto;
import com.eduTech.eduTech.dto.SubmitQuizRequest;
import com.eduTech.eduTech.entity.ClassEntity;
import com.eduTech.eduTech.entity.QuizOption;
import com.eduTech.eduTech.entity.QuizResult;
import com.eduTech.eduTech.entity.User;
import com.eduTech.eduTech.repository.ClassRepository;
import com.eduTech.eduTech.repository.QuizOptionRepository;
import com.eduTech.eduTech.repository.QuizResultRepository;
import com.eduTech.eduTech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizAttemptService {

    private final QuizOptionRepository optionRepository;
    private final QuizResultRepository resultRepository;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final QuizService quizService;

    public List<QuizQuestionDto> getQuestionsForStudent(String email) {
        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (student.getStudentClass() == null) {
            throw new RuntimeException("Student class is not assigned");
        }

        ClassEntity classEntity = classRepository.findByClassName(student.getStudentClass())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        return quizService.getQuestionsWithOptionsForClass(classEntity.getId());
    }

    public QuizResult submitAnswer(SubmitQuizRequest request) {

        QuizOption option = optionRepository.findById(request.getSelectedOptionId())
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
