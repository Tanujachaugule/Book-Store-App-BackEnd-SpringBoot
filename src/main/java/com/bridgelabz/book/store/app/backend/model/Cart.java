package com.bridgelabz.book.store.app.backend.model;


import com.bridgelabz.book.store.app.backend.dto.CartDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "/cart_table")
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private long cardId;
    @OneToOne
    private User user;
    @ManyToOne
    private Book book;

    private long quantity;

    private long totalPrice;
    public Cart(User user, Book book, CartDTO cartDTO) {
        this.user = user;
        this.book = book;
        this.setQuantity(cartDTO.getQuantity());
        this.setTotalPrice(this.getQuantity() * this.getBook().getBookPrice());
    }

    public void setPrice(long l) {
    }
}
