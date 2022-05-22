package com.hyperskill.webquizengine.controller;

import com.hyperskill.webquizengine.model.Quiz;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class QuizController {

    @GetMapping("/api/quiz")
    public ResponseEntity<Quiz> getQuiz(){
        Quiz quiz = new Quiz(
                "First Programmer",
                "Who was the first programmer?",
                List.of("Alan Turing", "Steve Jobs", "Ada Lovelace", "Linus Torvalds"));

        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/api/quiz")
    public ResponseEntity<Map<String,String>> getAnswer(@RequestParam int answer){
        HashMap<String,String> quizAnswer = new HashMap<>();
        if(answer == 2){
            quizAnswer.put("success","true");
            quizAnswer.put("feedback","Congratulations, you're right!");
        }
        else{
            quizAnswer.put("success","false");
            quizAnswer.put("feedback","Not correct, try again!");
        }

        return ResponseEntity.ok(quizAnswer);
    }
}
