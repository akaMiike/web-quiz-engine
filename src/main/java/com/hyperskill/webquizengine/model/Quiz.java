package com.hyperskill.webquizengine.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Quiz {
    private String title;
    private String text;
    private List<String> options;

    public Quiz(String title, String text, List<String> options){
        this.title = title;
        this.text = text;
        this.options = options;
    }
}
