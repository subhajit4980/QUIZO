package com.subhajit.Quizservice.Controller;

import com.subhajit.Quizservice.Model.QuestionWrapper;
import com.subhajit.Quizservice.Model.Quiz;
import com.subhajit.Quizservice.Model.QuizDto;
import com.subhajit.Quizservice.Model.Response;
import com.subhajit.Quizservice.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @GetMapping("/")
    public ResponseEntity<List<Quiz>> getQuizList()
    {
        return quizService.getQuizList();
    }
    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle());
    }

    @PostMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }
    @CrossOrigin("*")
    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateScore(id, responses);
    }

}
