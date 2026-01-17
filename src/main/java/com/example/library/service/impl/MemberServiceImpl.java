package com.example.library.service.impl;

import com.example.library.dto.MemberCreateDto;
import com.example.library.dto.MemberDto;
import com.example.library.dto.MemberUpdateDto;
import com.example.library.entity.Member;
import com.example.library.exception.NotFoundException;
import com.example.library.mapper.MemberMapper;
import com.example.library.repository.MemberRepository;
import com.example.library.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repo;
    private final MemberMapper mapper;

    @Override
    public MemberDto create(MemberCreateDto dto) {
        Member member = mapper.toEntity(dto);
        return mapper.toDto(repo.save(member));
    }

    @Override
    public MemberDto update(Long id, MemberUpdateDto dto) {
        Member member = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Member not found"));
        mapper.updateFromDto(dto, member);
        return mapper.toDto(repo.save(member));
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDto getById(Long id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Member not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberDto> list(String q, Pageable pageable) {

        // ⚠️ PROTECTION OBLIGATOIRE
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }

        return repo.findAll(pageable)
                .map(mapper::toDto);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Member not found");
        }
        repo.deleteById(id);
    }
}
