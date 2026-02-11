package com.eduTech.eduTech.controller;

import com.eduTech.eduTech.dto.QuizQuestionDto;
import com.eduTech.eduTech.dto.SubmitQuizRequest;
import com.eduTech.eduTech.entity.QuizResult;
import com.eduTech.eduTech.service.QuizAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/student/quiz")
@RequiredArgsConstructor
public class StudentQuizController {

    private final QuizAttemptService quizAttemptService;

    @PostMapping("/submit")
    public QuizResult submit(@RequestBody SubmitQuizRequest request) {
        return quizAttemptService.submitAnswer(request);
    }

    @GetMapping("/questions")
    public List<QuizQuestionDto> getQuestions(Principal principal) {
        return quizAttemptService.getQuestionsForStudent(principal.getName());
    }

    @GetMapping("/search")
    public List<QuizQuestionDto> search(@RequestParam String subject, Principal principal) {
        return quizAttemptService.getQuestionsBySubjectName(principal.getName(), subject);
    }
}
