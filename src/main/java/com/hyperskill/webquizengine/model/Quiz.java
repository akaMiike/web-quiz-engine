package com.hyperskill.webquizengine.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Quiz {
    private int id;

    private String title;
    private String text;
    private List<String> options;
    private List<Integer> answer;

    public Quiz(){}

    public Quiz(int id, String title, String text, List<String> options, List<Integer> answer){
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public Quiz(String title, String text, List<String> options){
        new Quiz(0,title,text,options,List.of());
    }

}
