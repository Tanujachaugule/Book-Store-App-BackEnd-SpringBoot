package com.bridgelabz.book.store.app.backend.controller;

import com.bridgelabz.book.store.app.backend.dto.CartDTO;
import com.bridgelabz.book.store.app.backend.dto.ResponseDTO;
import com.bridgelabz.book.store.app.backend.model.Cart;
import com.bridgelabz.book.store.app.backend.model.User;
import com.bridgelabz.book.store.app.backend.service.CartService;
import com.bridgelabz.book.store.app.backend.service.UserService;
import com.bridgelabz.book.store.app.backend.util.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")

public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTToken jwtToken;

    @PostMapping("/addCart")
    public ResponseEntity<ResponseDTO> addToCart(@RequestBody CartDTO cartDTO) {
        ResponseDTO cart = cartService.addToCart(cartDTO);
        ResponseDTO responseDTO = new ResponseDTO("Book added to cart successfully", cart);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getCard/{cartId}")
    public ResponseEntity<ResponseDTO> getCartById(@PathVariable long cartId) {
        User cart = cartService.getCartById(cartId);
        ResponseDTO responseDTO;
        if (cart != null) {
            responseDTO = new ResponseDTO("Cart fetched successfully", cart);
        } else {
            responseDTO = new ResponseDTO("Cart not found", null);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<ResponseDTO> deleteCart(@PathVariable long cartId) {
        cartService.deleteCartById(cartId);
        ResponseDTO responseDTO = new ResponseDTO("Cart deleted successfully", null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/removeByUser")
    public ResponseEntity<ResponseDTO> removeCartItemsByUser(@RequestHeader("Authorization") String token) {
        User user = getUserFromToken(token);
        if (user != null) {
            cartService.removeCartItemsByUser(token);
            ResponseDTO responseDTO = new ResponseDTO("Cart items removed for user", null);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            ResponseDTO responseDTO = new ResponseDTO("Unauthorized", null);
            return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping("/updateQuantity/{cartId}")
    public ResponseEntity<ResponseDTO> updateCartItemQuantity(@RequestHeader("Authorization") String token,
                                                              @PathVariable long cartId,
                                                              @RequestParam long quantity) {
        User user = getUserFromToken(token);
        if (user != null) {
            Cart updatedCart = cartService.updateCartItemQuantity(token, cartId, quantity);
            if (updatedCart != null) {
                ResponseDTO responseDTO = new ResponseDTO("Cart item quantity updated", updatedCart);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else {
                ResponseDTO responseDTO = new ResponseDTO("Cart item not found or unauthorized", null);
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            }
        } else {
            ResponseDTO responseDTO = new ResponseDTO("Unauthorized", null);
            return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("getAllCarts")
    public ResponseEntity<ResponseDTO> getAllCarts() {
        List<Cart> allCarts = cartService.getAllCarts();
        ResponseDTO responseDTO = new ResponseDTO("Books retrieved successfully", allCarts);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }



    @GetMapping("/getAllByUser")
    public ResponseEntity<ResponseDTO> getAllCartItemsForUser(@RequestHeader("Authorization") String token) {
        User user = getUserFromToken(token);
        if (user != null) {
            List<Cart> cartItems = cartService.getAllCartItemsForUser(token);
            ResponseDTO responseDTO = new ResponseDTO("Cart items retrieved successfully", cartItems);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            ResponseDTO responseDTO = new ResponseDTO("Unauthorized", null);
            return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
        }
    }

    // ... (other mappings)

    private User getUserFromToken(String token) {
        try {
            long userId = jwtToken.decodeToken(token);
            return userService.getUserById((int) userId);
        } catch (Exception e) {
            return null; // Token decoding or user retrieval failed
        }
    }
}