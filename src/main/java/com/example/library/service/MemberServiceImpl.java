package com.example.library.service;

import com.example.library.dto.MemberRequestDTO;
import com.example.library.dto.MemberResponseDTO;
import com.example.library.exception.ConflictException;
import com.example.library.exception.ResourceNotFoundException;
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
        // conflit sur email
        memberRepository.findByEmail(dto.getEmail()).ifPresent(m -> {
            throw new ConflictException("Member with email " + dto.getEmail() + " already exists");
        });
        Member m = MemberMapper.toEntity(dto);
        Member saved = memberRepository.save(m);
        return MemberMapper.toDto(saved);
    }

    @Override
    public MemberResponseDTO getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id " + id));
        return MemberMapper.toDto(member);
    }

    @Override
    public Page<MemberResponseDTO> getAllMembers(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        return new PageImpl<>(page.getContent().stream().map(MemberMapper::toDto).collect(Collectors.toList()), pageable, page.getTotalElements());
    }

    @Override
    public MemberResponseDTO updateMember(Long id, MemberRequestDTO dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update — member not found with id " + id));


        if (dto.getEmail() != null && !dto.getEmail().equals(member.getEmail())) {
            memberRepository.findByEmail(dto.getEmail()).ifPresent(existing -> {
                throw new ConflictException("Another member already uses email " + dto.getEmail());
            });
        }

        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        Member saved = memberRepository.save(member);
        return MemberMapper.toDto(saved);
    }

    @Override
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete — member not found with id " + id);
        }
        memberRepository.deleteById(id);
    }
}