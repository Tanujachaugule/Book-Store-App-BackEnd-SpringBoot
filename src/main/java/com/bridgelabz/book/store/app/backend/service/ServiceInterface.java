package com.bridgelabz.book.store.app.backend.service;

import com.bridgelabz.book.store.app.backend.dto.CartDTO;
import com.bridgelabz.book.store.app.backend.dto.ResponseDTO;
import com.bridgelabz.book.store.app.backend.model.Cart;
import com.bridgelabz.book.store.app.backend.model.User;

import java.util.List;

public interface ServiceInterface {
    ResponseDTO addToCart(CartDTO cartDTO);

    User getCartById(long cartId);

    void deleteCartById(long cartId);

    void removeCartItemsByUser(String token);

    Cart updateCartItemQuantity(String token, long cartId, long quantity);

    List<Cart> getAllCartItemsForUser(String token);

    List<Cart> getAllCarts();

    // You can add more common methods here if needed

}
