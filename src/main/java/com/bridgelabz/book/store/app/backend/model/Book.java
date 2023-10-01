package com.bridgelabz.book.store.app.backend.model;


import com.bridgelabz.book.store.app.backend.dto.BookDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue
    private int bookId;
    private String bookName;

    private String bookDescription;

    private long bookPrice;
    private int bookQuantity;
    public Book(BookDTO bookDTO) {
        this.updateBook(bookDTO);
    }

    public void updateBook(BookDTO bookDTO) {
        this.bookName=bookDTO.getBookName();

        this.bookDescription=     bookDTO.getBookDescription();

        this.bookPrice=  bookDTO.getBookPrice();
        this.bookQuantity  = bookDTO.getBookQuantity();
    }
}
