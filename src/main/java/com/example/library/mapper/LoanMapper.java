package com.example.library.mapper;

import com.example.library.dto.LoanDto;
import com.example.library.entity.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "bookId", expression = "java(getBookId(loan))")
    @Mapping(target = "bookTitle", expression = "java(getBookTitle(loan))")
    @Mapping(target = "memberId", expression = "java(getMemberId(loan))")
    @Mapping(target = "memberName", expression = "java(getMemberName(loan))")
    @Mapping(target = "loanDate", expression = "java(toString(loan.getLoanDate()))")
    @Mapping(target = "dueDate", expression = "java(toString(loan.getDueDate()))")
    @Mapping(target = "returnedDate", expression = "java(toString(loan.getReturnedDate()))")
    @Mapping(target = "status", source = "status")
    LoanDto toDto(Loan loan);

    // ===== HELPERS =====
    default Long getBookId(Loan loan) {
        return loan.getBook() != null ? loan.getBook().getId() : null;
    }

    default String getBookTitle(Loan loan) {
        return loan.getBook() != null ? loan.getBook().getTitle() : null;
    }

    default Long getMemberId(Loan loan) {
        return loan.getMember() != null ? loan.getMember().getId() : null;
    }

    default String getMemberName(Loan loan) {
        return loan.getMember() != null ? loan.getMember().getFullName() : null;
    }

    default String toString(java.time.Instant date) {
        return date != null ? date.toString() : null;
    }
}
