package com.example.library.service;

import com.example.library.dto.*;
import org.springframework.data.domain.*;

public interface MemberService {

    MemberDto create(MemberCreateDto dto);

    MemberDto update(Long id, MemberUpdateDto dto);

    MemberDto getById(Long id);

    Page<MemberDto> list(String q, Pageable pageable);

    void delete(Long id);
}
