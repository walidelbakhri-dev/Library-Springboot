package com.example.library.service;

import com.example.library.dto.LoanRequestDTO;
import com.example.library.dto.LoanResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoanService {
    LoanResponseDTO createLoan(LoanRequestDTO dto);
    LoanResponseDTO getLoanById(Long id);
    Page<LoanResponseDTO> getAllLoans(Pageable pageable);
    LoanResponseDTO returnLoan(Long id); // mark returnedDate = now
    void deleteLoan(Long id);
}