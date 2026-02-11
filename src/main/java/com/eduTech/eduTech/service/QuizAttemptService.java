package com.eduTech.eduTech.service;

import com.eduTech.eduTech.dto.QuizQuestionDto;
import com.eduTech.eduTech.dto.SubmitQuizRequest;
import com.eduTech.eduTech.entity.ClassEntity;
import com.eduTech.eduTech.entity.QuizOption;
import com.eduTech.eduTech.entity.QuizQuestion;
import com.eduTech.eduTech.entity.QuizResult;
import com.eduTech.eduTech.entity.Subject;
import com.eduTech.eduTech.entity.User;
import com.eduTech.eduTech.repository.ClassRepository;
import com.eduTech.eduTech.repository.QuizOptionRepository;
import com.eduTech.eduTech.repository.QuizQuestionRepository;
import com.eduTech.eduTech.repository.QuizResultRepository;
import com.eduTech.eduTech.repository.SubjectRepository;
import com.eduTech.eduTech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizAttemptService {

    private final QuizOptionRepository optionRepository;
    private final QuizResultRepository resultRepository;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final QuizService quizService; // Note: QuizAttemptService depends on QuizService? Or should it use
                                           // Repository?
    // Ideally Service should use Repository, avoiding circular dependency if
    // QuizService uses QuizAttemptService.
    // QuizService doesn't appear to use QuizAttemptService.
    private final SubjectRepository subjectRepository;
    private final QuizQuestionRepository questionRepository; // Injecting repo to query by subject

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

    public List<QuizQuestionDto> getQuestionsBySubjectName(String email, String subjectName) {
        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (student.getStudentClass() == null) {
            throw new RuntimeException("Student class is not assigned");
        }

        ClassEntity classEntity = classRepository.findByClassName(student.getStudentClass())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        // Find subject by name (case insensitive)
        Subject subject = subjectRepository.findByClassId(classEntity.getId()).stream()
                .filter(s -> s.getSubjectName().equalsIgnoreCase(subjectName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Subject not found: " + subjectName));

        // Find questions
        List<QuizQuestion> questions = questionRepository.findByClassIdAndSubjectId(classEntity.getId(),
                subject.getId());

        // Map to DTO (Duplicated logic from QuizService, but simplest for now without
        // helper)
        List<QuizQuestionDto> response = new ArrayList<>();
        for (QuizQuestion q : questions) {
            QuizQuestionDto dto = new QuizQuestionDto();
            dto.setId(q.getId());
            dto.setQuestionText(q.getQuestionText());
            dto.setOptions(optionRepository.findByQuestionId(q.getId()));
            response.add(dto);
        }
        return response;
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
