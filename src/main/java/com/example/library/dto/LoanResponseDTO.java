package com.example.library.dto;

import java.time.LocalDate;

public class LoanResponseDTO {
    private Long id;
    private Long bookId;
    private Long memberId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    public LoanResponseDTO() {}
    public LoanResponseDTO(Long id, Long bookId, Long memberId, LocalDate loanDate, LocalDate dueDate, LocalDate returnedDate) {
        this.id = id; this.bookId = bookId; this.memberId = memberId;
        this.loanDate = loanDate; this.dueDate = dueDate; this.returnedDate = returnedDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public LocalDate getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDate loanDate) { this.loanDate = loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getReturnedDate() { return returnedDate; }
    public void setReturnedDate(LocalDate returnedDate) { this.returnedDate = returnedDate; }
}