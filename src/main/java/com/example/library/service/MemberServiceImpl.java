package com.example.library.service;

import com.example.library.dto.MemberRequestDTO;
import com.example.library.dto.MemberResponseDTO;
import com.example.library.Mapper.MemberMapper;
import com.example.library.model.Member;
import com.example.library.Repository.MemberRepository;
import com.example.library.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    public MemberServiceImpl(MemberRepository memberRepository) { this.memberRepository = memberRepository; }

    @Override
    public MemberResponseDTO createMember(MemberRequestDTO dto) {
        Member m = MemberMapper.toEntity(dto);
        Member saved = memberRepository.save(m);
        return MemberMapper.toDto(saved);
    }

    @Override
    public MemberResponseDTO getMemberById(Long id) {
        return memberRepository.findById(id).map(MemberMapper::toDto).orElse(null);
    }

    @Override
    public Page<MemberResponseDTO> getAllMembers(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        return new PageImpl<>(page.getContent().stream().map(MemberMapper::toDto).collect(Collectors.toList()), pageable, page.getTotalElements());
    }

    @Override
    public MemberResponseDTO updateMember(Long id, MemberRequestDTO dto) {
        return memberRepository.findById(id).map(member -> {
            member.setName(dto.getName());
            member.setEmail(dto.getEmail());
            return MemberMapper.toDto(memberRepository.save(member));
        }).orElse(null);
    }

    @Override
    public void deleteMember(Long id) { memberRepository.deleteById(id); }
}