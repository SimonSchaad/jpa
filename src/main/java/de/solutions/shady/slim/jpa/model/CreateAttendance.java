package de.solutions.shady.slim.jpa.model;

import java.time.LocalDateTime;

public class CreateAttendance {
    private LocalDateTime date;
    private Long userId;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
