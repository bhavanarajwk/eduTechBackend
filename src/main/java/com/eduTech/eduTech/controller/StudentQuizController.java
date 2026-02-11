package com.eduTech.eduTech.controller;

import com.eduTech.eduTech.dto.SubmitQuizRequest;
import com.eduTech.eduTech.entity.QuizResult;
import com.eduTech.eduTech.service.QuizAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student/quiz")
@RequiredArgsConstructor
public class StudentQuizController {

    private final QuizAttemptService quizAttemptService;

    @PostMapping("/submit")
    public QuizResult submit(@RequestBody SubmitQuizRequest request){
        return quizAttemptService.submitAnswer(request);
    }
}

