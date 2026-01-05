package com.example.library.Mapper;

import com.example.library.dto.MemberRequestDTO;
import com.example.library.dto.MemberResponseDTO;
import com.example.library.model.Member;

public class MemberMapper {
    public static Member toEntity(MemberRequestDTO dto) {
        if (dto == null) return null;
        Member m = new Member();
        m.setName(dto.getPrenom());
        m.setEmail(dto.getEmail());
        return m;
    }

    public static MemberResponseDTO toDto(Member entity) {
        if (entity == null) return null;
        return new MemberResponseDTO(entity.getId(), entity.getName(), entity.getEmail());
    }
}