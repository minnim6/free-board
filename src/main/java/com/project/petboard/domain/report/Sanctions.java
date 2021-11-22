package com.project.petboard.domain.report;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Sanctions{

    @Id
    private String sanctionsKey;

    private int sanctionsValue;

    private String sanctionsContents;
}
