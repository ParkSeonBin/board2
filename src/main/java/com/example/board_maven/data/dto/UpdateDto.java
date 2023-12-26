package com.example.board_maven.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateDto {
    private Long id;
    private String title;
    private String content;
}
