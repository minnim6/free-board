package com.project.petboard.domain.image;

import com.project.petboard.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author 미정
 * @ 20211202
 * @since 0.0.1
 */
@Getter
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
