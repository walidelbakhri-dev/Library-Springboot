package com.example.library.service;

import com.example.library.dto.MemberRequestDTO;
import com.example.library.dto.MemberResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {
    MemberResponseDTO createMember(MemberRequestDTO dto);
    MemberResponseDTO getMemberById(Long id);
    Page<MemberResponseDTO> getAllMembers(Pageable pageable);
    MemberResponseDTO updateMember(Long id, MemberRequestDTO dto);
    void deleteMember(Long id);
}