package com.richmillionaire.richmillionaire.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBlockRequest {
    private String data;
    private String minedBy;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
