package com.hyperskill.webquizengine.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.ZonedDateTime;
import java.util.List;

public class QuizCompletionInfoDTO {
    private long id;
    private ZonedDateTime completedAt;

    public QuizCompletionInfoDTO(long id, ZonedDateTime completedAt) {
        this.id = id;
        this.completedAt = completedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ZonedDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(ZonedDateTime completedAt) {
        this.completedAt = completedAt;
    }

}
