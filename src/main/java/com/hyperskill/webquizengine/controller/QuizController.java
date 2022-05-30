package com.hyperskill.webquizengine.controller;

import com.hyperskill.webquizengine.dto.QuizCreationDTO;
import com.hyperskill.webquizengine.dto.QuizReturnDTO;
import com.hyperskill.webquizengine.model.Quiz;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class QuizController {

    private static List<Quiz> allQuizzes = new ArrayList<>();
    private static int id = 0;

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

    @PostMapping("/api/quizzes")
    public ResponseEntity<QuizReturnDTO> createQuiz(@RequestBody @Valid QuizCreationDTO quiz){
        for(Integer answer : quiz.getAnswer()){
            if(answer >= quiz.getOptions().size() || answer < 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"index of answer is not in the options.");
            }
        }

        Quiz newQuiz = new Quiz(++id, quiz.getTitle(), quiz.getText(), quiz.getOptions(), quiz.getAnswer());
        allQuizzes.add(newQuiz);

        QuizReturnDTO newQuizDTO = new QuizReturnDTO(newQuiz.getId(), newQuiz.getTitle(), newQuiz.getText(), newQuiz.getOptions());
        return ResponseEntity.ok(newQuizDTO);

    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<QuizReturnDTO> getQuizById(@PathVariable("id") int id){
        QuizReturnDTO wantedQuiz;
        for(Quiz quiz: allQuizzes){
            if(quiz.getId() == id){
                wantedQuiz = new QuizReturnDTO(id, quiz.getTitle(), quiz.getText(), quiz.getOptions());
                return ResponseEntity.ok(wantedQuiz);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Quiz not found.");
    }

    @GetMapping("/api/quizzes")
    public ResponseEntity<List<QuizReturnDTO>> getQuizzes(){
        ArrayList<QuizReturnDTO> allQuizzesDTO = new ArrayList<>();
        for(Quiz quiz: allQuizzes){
            allQuizzesDTO.add(new QuizReturnDTO(quiz.getId(), quiz.getTitle(), quiz.getText(), quiz.getOptions()));
        }

        return ResponseEntity.ok(allQuizzesDTO);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<Map<String,Object>> solveQuiz(@PathVariable("id") int id, @RequestBody Map<String,List<Integer>> map){
        List<Integer> answers = (map.get("answer") == null) ? List.of() : map.get("answer");
        for(Quiz quiz: allQuizzes){
            if(quiz.getId() == id){

                if(quiz.getAnswer().equals(answers)){
                    return ResponseEntity.ok(
                            Map.of("success",true, "feedback","Congratulations, you're right!")
                    );
                }

                else{
                    return ResponseEntity.ok(
                            Map.of("success",false, "feedback","Wrong answer! Please, try again.")
                    );
                }
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Quiz not found.");
    }


}
