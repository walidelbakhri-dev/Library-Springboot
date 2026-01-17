package com.example.library.mapper;

import com.example.library.dto.MemberCreateDto;
import com.example.library.dto.MemberDto;
import com.example.library.dto.MemberUpdateDto;
import com.example.library.entity.Member;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberDto toDto(Member member);

    Member toEntity(MemberCreateDto dto);

    void updateFromDto(MemberUpdateDto dto, @MappingTarget Member entity);
}
