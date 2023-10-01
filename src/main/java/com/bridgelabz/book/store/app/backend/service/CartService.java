package com.bridgelabz.book.store.app.backend.service;

import com.bridgelabz.book.store.app.backend.dto.CartDTO;
import com.bridgelabz.book.store.app.backend.dto.ResponseDTO;
import com.bridgelabz.book.store.app.backend.exception.BookCustomException;
import com.bridgelabz.book.store.app.backend.exception.UserCustomException;
import com.bridgelabz.book.store.app.backend.model.Book;
import com.bridgelabz.book.store.app.backend.model.Cart;
import com.bridgelabz.book.store.app.backend.model.User;
import com.bridgelabz.book.store.app.backend.repository.BookRepo;
import com.bridgelabz.book.store.app.backend.repository.CartRepo;
import com.bridgelabz.book.store.app.backend.repository.UserRepo;
import com.bridgelabz.book.store.app.backend.util.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service

public class CartService implements ServiceInterface{

    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JWTToken jwtToken;

    @Autowired
    private UserService userService;

    public ResponseDTO addToCart(CartDTO cartDTO) {
        // Get the user and book based on IDs from cartDTO
        User user = userRepo.findById((int) cartDTO.getUser())
                .orElseThrow(() -> new UserCustomException("User not found with ID: " + cartDTO.getUser()));
        Book book = bookRepo.findById(cartDTO.getBook())
                .orElseThrow(() -> new BookCustomException("Book not found with ID: " + cartDTO.getBook()));
        Cart cart = new Cart(user, book, cartDTO);
        cartRepo.save(cart);
        return new ResponseDTO(cart);
    }

    public User getCartById(long cartId) {
        return userRepo.findById((int) cartId).orElseThrow(() -> new UserCustomException("user with id: " + cartId + "not present"));
    }

    public void deleteCartById(long cartId) {
        cartRepo.deleteById((int) cartId);
    }

    private User getUserFromToken(String token) {
        try {
            long userId = jwtToken.decodeToken(token);
            return userService.getUserById((int) userId);
        } catch (Exception e) {
            return null; // Token decoding or user retrieval failed
        }
    }

    @Override
    public void removeCartItemsByUser(String token) {
        User user = getUserFromToken(token);

        if (user != null) {
            List<Cart> cartItems = cartRepo.findByUserId(user.getId());
            cartRepo.deleteAll(cartItems);
        }
    }

    @Override
    public Cart updateCartItemQuantity(String token, long cartId, long quantity) {
        User user = getUserFromToken(token);

        if (user != null) {
            Cart cart = cartRepo.findByCartIdAndUserId(cartId, user.getId());
            if (cart != null) {
                cart.setQuantity(quantity);
                cart.setPrice(cart.getBook().getBookPrice() * quantity);
                return cartRepo.save(cart);
            }
        }
        return null;
    }

    @Override
    public List<Cart> getAllCartItemsForUser(String token) {
        User user = getUserFromToken(token);

        if (user != null) {
            return cartRepo.findByUserId(user.getId());
        }
        return Collections.emptyList();
    }

    // ... (other methods)


    public List<Cart> getAllCarts() {
        return cartRepo.findAll();
    }
}