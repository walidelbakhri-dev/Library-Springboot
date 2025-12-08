package com.example.library.Mapper;

import com.example.library.dto.BookRequestDTO;
import com.example.library.dto.BookResponseDTO;
import com.example.library.model.Book;

public class BookMapper {
    public static Book toEntity(BookRequestDTO dto) {
        if (dto == null) return null;
        Book b = new Book();
        b.setTitle(dto.getTitle());
        b.setAuthor(dto.getAuthor());
        b.setIsbn(dto.getIsbn());
        return b;
    }

    public static BookResponseDTO toDto(Book entity) {
        if (entity == null) return null;
        return new BookResponseDTO(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getIsbn());
    }

    public static void updateEntity(Book entity, BookRequestDTO dto) {
        if (dto == null || entity == null) return;
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setIsbn(dto.getIsbn());
    }
}