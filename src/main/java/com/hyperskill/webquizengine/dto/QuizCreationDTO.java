package com.hyperskill.webquizengine.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class QuizCreationDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @Size(min = 2)
    @NotNull
    private List<String> options;

    private List<Integer> answer = List.of();

    public QuizCreationDTO() {
    }

    public QuizCreationDTO(String title, String text, List<String> options, List<Integer> answers) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswers(List<Integer> answers) {
        this.answer = (answers == null) ? List.of() : answer;
    }
}
