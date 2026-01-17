package com.example.library.mapper;

import com.example.library.dto.*;
import com.example.library.entity.Book;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Book book);

    Book toEntity(BookCreateDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(BookUpdateDto dto, @MappingTarget Book entity);
}
