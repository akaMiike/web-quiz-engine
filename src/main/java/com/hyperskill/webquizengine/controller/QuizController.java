package com.hyperskill.webquizengine.controller;

import com.hyperskill.webquizengine.dto.*;
import com.hyperskill.webquizengine.model.Quiz;
import com.hyperskill.webquizengine.model.QuizCompletionInfo;
import com.hyperskill.webquizengine.model.User;
import com.hyperskill.webquizengine.service.QuizCompletionInfoService;
import com.hyperskill.webquizengine.service.QuizService;
import com.hyperskill.webquizengine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class QuizController {

    @Autowired
    private QuizService quizService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuizCompletionInfoService quizCompletionInfoService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Endpoint just for testing in the first stages
    @GetMapping("/quiz")
    public ResponseEntity<Quiz> getQuiz(){
        Quiz quiz = new Quiz(
                "First Programmer",
                "Who was the first programmer?",
                List.of("Alan Turing", "Steve Jobs", "Ada Lovelace", "Linus Torvalds"),
                null
        );

        return ResponseEntity.ok(quiz);
    }
    //Endpoint just for testing in the first stages
    @PostMapping("/quiz")
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

    @PostMapping("/quizzes")
    public ResponseEntity<QuizInfoDTO> createQuiz(@RequestBody @Valid QuizCreationDTO quiz,
                                                  @AuthenticationPrincipal UserDetails userDetails){
        for(Integer answer : quiz.getAnswer()){
            if(answer >= quiz.getOptions().size() || answer < 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"index of answer is not in the options.");
            }
        }

        User loggedInUser = userService.findByEmail(userDetails.getUsername()).get();

        
        Quiz newQuiz = new Quiz(0, quiz.getTitle(), quiz.getText(), quiz.getOptions(), quiz.getAnswer(), loggedInUser);
        quizService.save(newQuiz);

        QuizInfoDTO newQuizResult = new QuizInfoDTO(newQuiz.getId(), newQuiz.getTitle(), newQuiz.getText(), newQuiz.getOptions());
        return ResponseEntity.ok(newQuizResult);

    }

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<QuizInfoDTO> getQuizById(@PathVariable("id") long id,
                                                   @AuthenticationPrincipal UserDetails userDetails){
        Optional<Quiz> quizQueryResult = quizService.findById(id);

        if(quizQueryResult.isPresent()){
            Quiz quiz = quizQueryResult.get();
            String loggedInUser = userDetails.getUsername();

            if(!quiz.getUser().getEmail().equals(loggedInUser)){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This quiz belongs to another user.");
            }

            QuizInfoDTO quizResult = new QuizInfoDTO(
                    id,
                    quiz.getTitle(),
                    quiz.getText(),
                    quiz.getOptions()
            );

            return ResponseEntity.ok(quizResult);
        }

        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Quiz not found.");
        }
    }

    @GetMapping("/quizzes")
    public ResponseEntity<Page<QuizInfoDTO>> getQuizzes(@RequestParam("page") int page){
        Page<Quiz> allQuizzes = quizService.findAll(page, 10);

        Page<QuizInfoDTO> allQuizzesDTO = new PageImpl<>(allQuizzes.getContent().stream()
                .map(quiz -> new QuizInfoDTO(quiz.getId(), quiz.getTitle(), quiz.getText(), quiz.getOptions())).collect(Collectors.toList()),
                PageRequest.of(page, 10), allQuizzes.getTotalElements());

        return ResponseEntity.ok(allQuizzesDTO);
    }

    @PostMapping("/quizzes/{id}/solve")
    public ResponseEntity<QuizFeedbackDTO> solveQuiz(@PathVariable("id") long id,
                                                        @RequestBody Map<String,List<Integer>> map,
                                                        @AuthenticationPrincipal UserDetails userDetails){
        List<Integer> answers = (map.get("answer") == null) ? List.of() : map.get("answer");
        Optional<Quiz> quizQueryResult = quizService.findById(id);

        if(quizQueryResult.isPresent()){
            Quiz quiz = quizQueryResult.get();
            String loggedInUserEmail = userDetails.getUsername();
            User loggedInUser = userService.findByEmail(loggedInUserEmail).get();

            if(quiz.getAnswer().containsAll(answers) && answers.containsAll(quiz.getAnswer())){
                QuizCompletionInfo newCompletion = new QuizCompletionInfo(ZonedDateTime.now(), quiz, loggedInUser);
                quizCompletionInfoService.save(newCompletion);

                return ResponseEntity.ok(
                        new QuizFeedbackDTO(true, "Congratulations, you're right!")
                );
            }

            else{
                return ResponseEntity.ok(
                        new QuizFeedbackDTO(false, "Wrong answer! Please, try again.")
                );
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Quiz not found.");
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserCreationDTO user){
        Optional<User> existingUser = userService.findByEmail(user.getEmail());

        if(existingUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email is already taken.");
        }

        User newUser = new User(user.getEmail(), passwordEncoder.encode(user.getPassword()));
        userService.save(newUser);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable("id") long id, @AuthenticationPrincipal UserDetails userDetails){
        Optional<Quiz> quizToBeDeleted = quizService.findById(id);

        if(quizToBeDeleted.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
        }

        User quizAuthor = quizToBeDeleted.get().getUser();
        String loggedInUserEmail = userDetails.getUsername();

        if(!quizAuthor.getEmail().equals(loggedInUserEmail)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't delete this quiz because you're not the author.");
        }

        quizService.delete(quizToBeDeleted.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/quizzes/completed")
    public ResponseEntity<Page<QuizCompletionInfoDTO>> getQuizCompletionInfo(@RequestParam("page") int page,
            @AuthenticationPrincipal UserDetails userDetails){

        String loggedInUserEmail = userDetails.getUsername();

        Page<QuizCompletionInfo> allCompletions = quizCompletionInfoService.findAllCompletionsByUserEmailPaged(
                loggedInUserEmail, page, 10, "completedAt", "DESC");

        Page<QuizCompletionInfoDTO> allCompletionsDTO = new PageImpl<>(allCompletions.getContent().stream()
                .map(completion -> new QuizCompletionInfoDTO(completion.getId(), completion.getCompletedAt()))
                .collect(Collectors.toList()));

        return ResponseEntity.ok(allCompletionsDTO);
    }
}
