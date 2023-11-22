package com.example.board_maven.data.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChangeBoardDto {

    private Long id;

    private String title;
    private String contents;
    private String useYn;

    public ChangeBoardDto(String title, String contents, String useYn) {
        this.title = title;
        this.contents = contents;
        this.useYn = useYn;
    }

    public ChangeBoardDto() {
    }
}
