package com.example.library.Mapper;

import com.example.library.dto.LoanResponseDTO;
import com.example.library.model.Loan;

public class LoanMapper {
    public static LoanResponseDTO toDto(Loan loan) {
        if (loan == null) return null;
        Long bookId = loan.getBook() != null ? loan.getBook().getId() : null;
        Long memberId = loan.getMember() != null ? loan.getMember().getId() : null;
        return new LoanResponseDTO(loan.getId(), bookId, memberId, loan.getLoanDate(), loan.getDueDate(), loan.getReturnedDate());
    }
}