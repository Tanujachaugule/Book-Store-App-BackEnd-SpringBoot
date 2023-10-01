package com.bridgelabz.book.store.app.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {


    private long user;
    private int book;
    private long quantity;
    private long  totalPrice;
}