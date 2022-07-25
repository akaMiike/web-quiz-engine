package com.hyperskill.webquizengine.repository;

import com.hyperskill.webquizengine.model.QuizCompletionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCompletionInfoRepository extends JpaRepository<QuizCompletionInfo, Long> {

    @Query(value = "SELECT completionInfo FROM QuizCompletionInfo completionInfo INNER JOIN completionInfo.quiz quiz INNER JOIN quiz.user user WHERE user.email = :email")
    Page<QuizCompletionInfo> findAllCompletionsByUserEmail(@Param("email") String email, Pageable pageable);
}
