package com.example.library.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {

    private Long id;

    // ===== BOOK =====
    private Long bookId;
    private String bookTitle;

    // ===== MEMBER =====
    private Long memberId;
    private String memberName;

    // ===== DATES =====
    private String loanDate;
    private String dueDate;
    private String returnedDate;

    private String status;
}
