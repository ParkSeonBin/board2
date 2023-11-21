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
public class BoardResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String useYn;
    private String createId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
