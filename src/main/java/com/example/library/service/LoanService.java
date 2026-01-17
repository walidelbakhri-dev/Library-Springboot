package com.example.library.service;

import com.example.library.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoanService {
    LoanDto createLoan(LoanCreateDto dto);
    LoanDto returnLoan(Long loanId);
    LoanDto getById(Long id);
    Page<LoanDto> list(String q, Pageable pageable);
    Page<LoanDto> listByMember(Long memberId, Pageable pageable);
}