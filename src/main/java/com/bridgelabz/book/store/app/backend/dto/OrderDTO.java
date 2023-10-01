package com.bridgelabz.book.store.app.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private LocalDate orderDate = LocalDate.now();
    private long price;
    private long quantity;
    private String address;
    private long user;
    private int book;
}
