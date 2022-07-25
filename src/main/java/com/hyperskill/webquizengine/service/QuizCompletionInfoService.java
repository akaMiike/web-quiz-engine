package com.hyperskill.webquizengine.service;

import com.hyperskill.webquizengine.model.QuizCompletionInfo;
import com.hyperskill.webquizengine.repository.QuizCompletionInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizCompletionInfoService {

    @Autowired
    QuizCompletionInfoRepository repository;

    public void save(QuizCompletionInfo completionInfo){
        repository.save(completionInfo);
    }

    public void delete(QuizCompletionInfo completionInfo){
        repository.delete(completionInfo);
    }

    public Optional<QuizCompletionInfo> findById(long id){
        return repository.findById(id);
    }

    public List<QuizCompletionInfo> findAll(){
        return repository.findAll();
    }

    public Page<QuizCompletionInfo> findAllCompletionsByUserEmailPaged(String email, int page, int pageSize, String sortBy, String sortOrder){
        Pageable pageInfoSorted = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        return repository.findAllCompletionsByUserEmail(email, pageInfoSorted);
    }

}
