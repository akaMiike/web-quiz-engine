package com.hyperskill.webquizengine.service;

import com.hyperskill.webquizengine.model.Quiz;
import com.hyperskill.webquizengine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public void save(Quiz quiz){
        quizRepository.save(quiz);
    }

    public void delete(Quiz quiz){
        quizRepository.delete(quiz);
    }

    public Optional<Quiz> findById(long id){
        return quizRepository.findById(id);
    }

    public List<Quiz> findAll(){
        return quizRepository.findAll();
    }

    public Page<Quiz> findAll(int page, int pageSize){
        Pageable pageInfo = PageRequest.of(page, pageSize);
        return quizRepository.findAll(pageInfo);
    }

    public Page<Quiz> findAll(int page, int pageSize, String sortBy, String sortOrder){
        Pageable pageInfoSorted = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        return quizRepository.findAll(pageInfoSorted);
    }
}
