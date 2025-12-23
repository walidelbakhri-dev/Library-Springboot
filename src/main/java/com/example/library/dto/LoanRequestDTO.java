package com.example.library.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

public class LoanRequestDTO {

    @NotNull(message = "bookId is required")
    private Long bookId;

    @NotNull(message = "memberId is required")
    private Long memberId;

    @NotNull(message = "loanDate is required")
    @FutureOrPresent(message = "loanDate cannot be in the past")
    private LocalDate loanDate;

    @NotNull(message = "dueDate is required")
    @FutureOrPresent(message = "dueDate must be today or in the future")
    private LocalDate dueDate;

    public LoanRequestDTO() {}

    public Long getBookId() {
        return bookId;
    }
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    public LocalDate getLoanDate() {
        return loanDate;
    }
    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}