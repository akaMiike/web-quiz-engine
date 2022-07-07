package com.hyperskill.webquizengine.dto;

public class QuizFeedbackDTO {

    private boolean success;
    private String feedback;
    
    public QuizFeedbackDTO(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }
}
