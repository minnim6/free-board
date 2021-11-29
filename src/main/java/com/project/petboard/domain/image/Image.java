package com.project.petboard.domain.image;

import com.project.petboard.domain.post.Post;
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
    private Post post;

    private String imageLocation;
}
