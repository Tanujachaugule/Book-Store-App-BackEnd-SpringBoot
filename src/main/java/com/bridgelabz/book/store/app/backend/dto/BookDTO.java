package com.bridgelabz.book.store.app.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    @Pattern(regexp="^[A-Z]{1}[a-zA-Z]{3,}$", message=" Name should be as per standard")
    private String bookName;

    private String bookDescription;

    @Min(value=100, message="book price shouble be more than 100")
    private int bookPrice;
    private int bookQuantity;
}
