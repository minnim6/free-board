package com.project.petboard.domain.board;

import com.project.petboard.domain.member.Member;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Board {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long BoardNumber;

    @OneToMany
    @JoinColumn
    private Member member;

    @Temporal(value = TemporalType.DATE) //년월일 date 타입 db에 매핑
    @Column(insertable = true, updatable = false)
    @CreationTimestamp
    private Data BoardCreateDate;

    private String BoardTitle;

    private String BoardContents;
    
    private String BoardCategory;

    @Temporal(value = TemporalType.DATE) //년월일 date 타입 db에 매핑
    @Column(insertable = true, updatable = true)
    @CreationTimestamp
    private Data BoardAmendDate;

    @Enumerated(EnumType.STRING)
    private BoardStatus boardStatus;
}
