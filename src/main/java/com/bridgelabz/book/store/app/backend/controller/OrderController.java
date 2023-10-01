package com.bridgelabz.book.store.app.backend.controller;

import com.bridgelabz.book.store.app.backend.dto.OrderDTO;
import com.bridgelabz.book.store.app.backend.dto.ResponseDTO;
import com.bridgelabz.book.store.app.backend.model.Order;
import com.bridgelabz.book.store.app.backend.model.User;
import com.bridgelabz.book.store.app.backend.service.OrderService;
import com.bridgelabz.book.store.app.backend.service.UserService;
import com.bridgelabz.book.store.app.backend.util.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTToken jwtToken;

    @PostMapping("/place")
    public ResponseEntity<ResponseDTO> placeOrder(@RequestHeader("Authorization") String token,
                                                  @RequestBody OrderDTO orderDTO) {
        ResponseDTO response = orderService.placeOrder(token, orderDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/cancel/{orderId}")
    public ResponseEntity<ResponseDTO> cancelOrder(@RequestHeader("Authorization") String token, @PathVariable int orderId) {
        User user = getUserFromToken(token);
        if (user != null) {
            boolean isCancelled = orderService.cancelOrder(user.getId(), orderId);
            if (isCancelled) {
                ResponseDTO responseDTO = new ResponseDTO("Order cancelled successfully", null);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else {
                ResponseDTO responseDTO = new ResponseDTO("Order not found or unauthorized", null);
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            }
        } else {
            ResponseDTO responseDTO = new ResponseDTO("Unauthorized", null);
            return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/getAllByUser")
    public ResponseEntity<ResponseDTO> getAllOrdersForUser(@RequestHeader("Authorization") String token) {
        User user = getUserFromToken(token);
        if (user != null) {
            List<Order> orders = orderService.getAllOrdersForUser(user.getId());
            ResponseDTO responseDTO = new ResponseDTO("Orders retrieved successfully", orders);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            ResponseDTO responseDTO = new ResponseDTO("Unauthorized", null);
            return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllOrders() {
        List<Order> orders = orderService.getAllOrders(false);
        ResponseDTO responseDTO = new ResponseDTO("Orders retrieved successfully", orders);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    private User getUserFromToken(String token) {
        try {
            long userId = jwtToken.decodeToken(token);
            return userService.getUserById((int) userId);
        } catch (Exception e) {
            return null; // Token decoding or user retrieval failed
        }
    }

}