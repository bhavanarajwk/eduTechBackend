package com.eduTech.eduTech.service;

import com.eduTech.eduTech.dto.CreateQuizRequest;
import com.eduTech.eduTech.dto.OptionDto;
import com.eduTech.eduTech.dto.QuizQuestionDto;
import com.eduTech.eduTech.entity.QuizOption;
import com.eduTech.eduTech.entity.QuizQuestion;
import com.eduTech.eduTech.repository.QuizOptionRepository;
import com.eduTech.eduTech.repository.QuizQuestionRepository;
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

    public QuizQuestion createQuiz(CreateQuizRequest request) {

        QuizQuestion question = new QuizQuestion();
        question.setQuestionText(request.getQuestionText());
        question.setClassId(request.getClassId());
        question.setSubjectId(request.getSubjectId());
        question.setCreatedAt(LocalDateTime.now());

        QuizQuestion savedQuestion = questionRepository.save(question);

        for (OptionDto opt : request.getOptions()) {
            QuizOption option = new QuizOption();
            option.setQuestionId(savedQuestion.getId());
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
        return questionRepository.findByClassId(classId);
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
        question.setQuestionText(request.getQuestionText());
        question.setClassId(request.getClassId());

        QuizQuestion updatedQuestion = questionRepository.save(question);

        // Delete old options
        optionRepository.deleteByQuestionId(id);

        // Add new options
        for (OptionDto opt : request.getOptions()) {
            QuizOption option = new QuizOption();
            option.setQuestionId(updatedQuestion.getId());
            option.setOptionText(opt.getOptionText());
            option.setIsCorrect(opt.getIsCorrect());
            optionRepository.save(option);
        }

        return updatedQuestion;
    }

    public List<QuizQuestionDto> getQuestionsWithOptionsForClass(Long classId) {
        List<QuizQuestion> questions = questionRepository.findByClassId(classId);
        List<QuizQuestionDto> response = new ArrayList<>();

        for (QuizQuestion q : questions) {
            QuizQuestionDto dto = new QuizQuestionDto();
            dto.setId(q.getId());
            dto.setQuestionText(q.getQuestionText());

            // Fetch options for this question
            List<QuizOption> options = optionRepository.findByQuestionId(q.getId());
            dto.setOptions(options); // Note: In a real app, might want to hide 'isCorrect' field

            response.add(dto);
        }
        return response;
    }

}
