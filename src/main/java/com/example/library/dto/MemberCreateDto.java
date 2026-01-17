package com.example.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MemberCreateDto {

    @NotBlank
    private String fullName;

    @Email
    private String email;

    private String phone;
}
