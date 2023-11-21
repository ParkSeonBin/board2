package com.example.board_maven.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
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

    private String contents;

    @Column(length = 1)
    private String useYn;

    @ManyToOne
    @JoinColumn(name = "createId")
    private BoardUser createId;
}
