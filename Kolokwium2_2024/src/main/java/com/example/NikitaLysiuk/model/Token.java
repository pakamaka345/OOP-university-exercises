package com.example.NikitaLysiuk.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class Token {
    public UUID getToken() {
        return token;
    }

    public LocalTime getTime() {
        return time;
    }

    public Status getStatus() {
        return status;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private UUID token;
    private LocalTime time;
    private Status status;
    public Token(UUID token, LocalTime time, Status status) {
        this.token = token;
        this.time = time;
        this.status = status;
    }

    public enum Status { Active, NonActive}
}
