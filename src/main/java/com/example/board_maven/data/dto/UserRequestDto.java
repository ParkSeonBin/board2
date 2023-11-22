package com.example.board_maven.data.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserRequestDto {
    private String userId;
    private String pwd;
}
