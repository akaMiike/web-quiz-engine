package com.hyperskill.webquizengine.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "QuizCompletionInfo")
public class QuizCompletionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "completedAt")
    private ZonedDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public QuizCompletionInfo(){}

    public QuizCompletionInfo(ZonedDateTime completedAt, Quiz quiz) {
        this.completedAt = completedAt;
        this.quiz = quiz;
    }
}
