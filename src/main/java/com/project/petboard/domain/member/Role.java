package com.project.petboard.domain.member;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
