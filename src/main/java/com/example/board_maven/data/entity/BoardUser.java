package com.example.board_maven.data.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "user")
public class BoardUser extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    private Long id;

    @Column(name = "userId",length = 50, unique = true)
    private String userId;

    private String pwd;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Role role;
}
