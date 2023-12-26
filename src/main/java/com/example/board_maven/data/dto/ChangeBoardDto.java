package com.example.board_maven.data.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChangeBoardDto {

    private Long id;

    private String title;
    private String content;
    private String useYn;

    public ChangeBoardDto(String title, String content, String useYn) {
        this.title = title;
        this.content = content;
        this.useYn = useYn;
    }

    public ChangeBoardDto() {
    }
}
