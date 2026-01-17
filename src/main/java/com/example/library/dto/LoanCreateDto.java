package com.example.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanCreateDto {
    public int getDueInDays;
    @NotNull
    private Long bookId;

    @NotNull
    private Long memberId;

    // optional: custom due days or date handling on backend
    private Integer dueInDays;

    public Long getBookId() {
        return 0L;
    }

    public Long getMemberId() {
        return 0L;
    }

    public int getDueInDays() {
        return 0;
    }
}

