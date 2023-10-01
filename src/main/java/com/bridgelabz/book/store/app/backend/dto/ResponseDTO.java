package com.bridgelabz.book.store.app.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDTO {

    private String message;
    private Object data;
    public ResponseDTO(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ResponseDTO(Object data) {
        this.data= data;
    }
}
