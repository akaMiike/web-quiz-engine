package com.hyperskill.webquizengine.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="Quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @ElementCollection
    @CollectionTable(name="options", joinColumns = @JoinColumn(name="id"))
    @Column(name = "options")
    private List<String> options;

    @ElementCollection
    @CollectionTable(name="answer", joinColumns = @JoinColumn(name="id"))
    @Column(name="answer")
    private List<Integer> answer;

    @ElementCollection
    @CollectionTable(name="completedAt", joinColumns = @JoinColumn(name="id"))
    @Column(name = "completedAt")
    private List<ZonedDateTime> completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    public Quiz(){}

    public Quiz(long id, String title, String text, List<String> options, List<Integer> answer, User user){
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
        this.user = user;
        this.completedAt = new ArrayList<>();
    }

    public Quiz(String title, String text, List<String> options, User user){
        new Quiz(0,title,text,options, List.of(), user);
    }

}
