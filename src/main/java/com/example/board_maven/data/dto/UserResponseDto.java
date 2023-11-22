package com.example.board_maven.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDto {
    private Long id;
    private String userId;
    private String pwd;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
