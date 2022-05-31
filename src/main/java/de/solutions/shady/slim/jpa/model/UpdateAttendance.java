package de.solutions.shady.slim.jpa.model;

import java.time.LocalDateTime;

public class UpdateAttendance {

    private Long id;

    private String date;
    private long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
