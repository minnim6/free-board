package com.project.petboard.domain.member;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Role {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long roleNumber;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_number")
    private Member member;

    private String role;

    public Role(Member member,String role) {
        this.member = member;
        this.role = role;
    }

}
