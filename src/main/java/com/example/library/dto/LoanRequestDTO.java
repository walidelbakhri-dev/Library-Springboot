package com.example.library.dto;

import java.time.LocalDate;

public class LoanRequestDTO {
    private Long bookId;
    private Long memberId;
    private LocalDate loanDate;
    private LocalDate dueDate;

    public LoanRequestDTO() {}
    public LoanRequestDTO(Long bookId, Long memberId, LocalDate loanDate, LocalDate dueDate) {
        this.bookId = bookId; this.memberId = memberId; this.loanDate = loanDate; this.dueDate = dueDate;
    }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public LocalDate getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDate loanDate) { this.loanDate = loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}