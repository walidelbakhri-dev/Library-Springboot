package com.example.library.dto;

import java.time.Instant;
import java.util.List;

public class ApiError {
    private final Instant timestamp = Instant.now();
    private int status;
    private String error;
    private String message;
    private String path;
    private List<String> errors;

    public ApiError() {}
    public ApiError(int status, String error, String message, String path) {
        this.status = status; this.error = error; this.message = message; this.path = path;
    }

    // getters / setters (omitted for brevity)
    public Instant getTimestamp() {
        return timestamp;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public List<String> getErrors() {
        return errors;
    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}