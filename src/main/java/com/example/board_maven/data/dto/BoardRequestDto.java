package com.example.board_maven.data.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BoardRequestDto {
    private String title;
    private String contents;
    private String useYn;
}
