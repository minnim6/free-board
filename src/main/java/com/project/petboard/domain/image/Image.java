package com.project.petboard.domain.image;

import com.project.petboard.domain.board.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Image {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long imageNumber;

    @ManyToOne
    @JoinColumn
    private Board board;

    private String imageLocation;
}
