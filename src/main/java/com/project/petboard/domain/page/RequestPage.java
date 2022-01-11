package com.project.petboard.domain.page;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RequestPage {

    private int page;
    private int size;

    public RequestPage(int page,int size){
        this.page = page;
        this.size = size;
    }
}
