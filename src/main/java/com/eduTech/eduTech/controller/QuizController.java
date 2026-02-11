package com.eduTech.eduTech.controller;

import com.eduTech.eduTech.dto.CreateQuizRequest;
import com.eduTech.eduTech.entity.QuizQuestion;
import com.eduTech.eduTech.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/create")
    public QuizQuestion createQuiz(@RequestBody CreateQuizRequest request){
        return quizService.createQuiz(request);
    }

    @GetMapping("/all")
    public List<QuizQuestion> getAll(){
        return quizService.getAllQuestions();
    }

    @GetMapping("/class/{classId}")
    public List<QuizQuestion> filterByClass(@PathVariable Long classId){
        return quizService.filterByClass(classId);
    }

    @GetMapping("/search")
    public List<QuizQuestion> search(@RequestParam String keyword){
        return quizService.searchQuestions(keyword);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        quizService.deleteQuestion(id);
    }
}

