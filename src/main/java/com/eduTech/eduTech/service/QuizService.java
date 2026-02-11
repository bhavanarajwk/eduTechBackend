package com.eduTech.eduTech.service;

import com.eduTech.eduTech.dto.CreateQuizRequest;
import com.eduTech.eduTech.dto.OptionDto;
import com.eduTech.eduTech.dto.QuizQuestionDto;
import com.eduTech.eduTech.entity.ClassEntity;
import com.eduTech.eduTech.entity.QuizOption;
import com.eduTech.eduTech.entity.QuizQuestion;
import com.eduTech.eduTech.entity.Subject;
import com.eduTech.eduTech.repository.ClassRepository;
import com.eduTech.eduTech.repository.QuizOptionRepository;
import com.eduTech.eduTech.repository.QuizQuestionRepository;
import com.eduTech.eduTech.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizQuestionRepository questionRepository;
    private final QuizOptionRepository optionRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;

    public QuizQuestion createQuiz(CreateQuizRequest request) {

        QuizQuestion question = new QuizQuestion();
        question.setQuestionText(request.getQuestionText());

        ClassEntity classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));
        question.setClassEntity(classEntity);

        if (request.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));
            question.setSubject(subject);
        }

        question.setCreatedAt(LocalDateTime.now());

        QuizQuestion savedQuestion = questionRepository.save(question);

        for (OptionDto opt : request.getOptions()) {
            QuizOption option = new QuizOption();
            option.setQuestion(savedQuestion);
            option.setOptionText(opt.getOptionText());
            option.setIsCorrect(opt.getIsCorrect());
            optionRepository.save(option);
        }

        return savedQuestion;
    }

    public List<QuizQuestion> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<QuizQuestion> filterByClass(Long classId) {
        return questionRepository.findByClassEntityId(classId);
    }

    public List<QuizQuestion> searchQuestions(String keyword) {
        return questionRepository.findByQuestionTextContainingIgnoreCase(keyword);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public QuizQuestion updateQuiz(Long id, CreateQuizRequest request) {

        QuizQuestion question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Update question fields
        // Update question fields
        question.setQuestionText(request.getQuestionText());

        ClassEntity classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));
        question.setClassEntity(classEntity);

        if (request.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));
            question.setSubject(subject);
        }

        QuizQuestion updatedQuestion = questionRepository.save(question);

        // Delete old options
        optionRepository.deleteByQuestionId(id);

        // Add new options
        for (OptionDto opt : request.getOptions()) {
            QuizOption option = new QuizOption();
            option.setQuestion(updatedQuestion);
            option.setOptionText(opt.getOptionText());
            option.setIsCorrect(opt.getIsCorrect());
            optionRepository.save(option);
        }

        return updatedQuestion;
    }

    public List<QuizQuestionDto> getQuestionsWithOptionsForClass(Long classId) {
        List<QuizQuestion> questions = questionRepository.findByClassEntityId(classId);
        List<QuizQuestionDto> response = new ArrayList<>();

        for (QuizQuestion q : questions) {
            QuizQuestionDto dto = new QuizQuestionDto();
            dto.setId(q.getId());
            dto.setQuestionText(q.getQuestionText());

            // Fetch options for this question
            // Using JPA mapping if easier, but keeping repo call for now or using
            // q.getOptions() if LAZY/EAGER
            // Since we defined @OneToMany(cascade=ALL), we can try q.getOptions()
            // But let's check fetch type. Default OneToMany is LAZY. OpenInView is enabled.
            // Let's safe bet: use repository or getter.
            List<QuizOption> options = q.getOptions();
            // If options is null (e.g. not initialized), might need fetch.
            // Actually, let's Stick to ID-based query updated to Entity-based query if we
            // didn't add mappedBy correctly.
            // We did: @OneToMany(mappedBy = "question").
            // So q.getOptions() should work if session is open.

            dto.setOptions(options);

            response.add(dto);
        }
        return response;
    }

}
