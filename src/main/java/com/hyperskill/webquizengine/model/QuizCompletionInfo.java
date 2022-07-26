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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "completedAt")
    private ZonedDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public QuizCompletionInfo(){}

    public QuizCompletionInfo(ZonedDateTime completedAt, Quiz quiz, User user) {
        this.completedAt = completedAt;
        this.quiz = quiz;
        this.user = user;
    }
}
