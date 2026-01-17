package com.example.library.dto;

import com.example.library.model.BookCondition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCreateDto {
    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String author;

    @Size(max = 100)
    private String isbn;

    private Integer year;

    private BookCondition condition;

    public BookCondition getCondition() {
        return condition;
    }

    public void setCondition(BookCondition condition) {
        this.condition = condition;
    }
}