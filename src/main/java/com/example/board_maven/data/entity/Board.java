package com.example.board_maven.data.entity;

import com.example.board_maven.data.dto.UpdateFormDto;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@DynamicUpdate
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "board")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    private Long id;

    @Column(length = 50)
    private String title;

    private String content;

    @Column(length = 1)
    @Enumerated(EnumType.STRING)
    private useYn useYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createId", referencedColumnName = "userId")
    private BoardUser createId;

    public void update(UpdateFormDto updateFormDto) {
        this.title = updateFormDto.getTitle();
        this.content = updateFormDto.getContent();
    }
}
