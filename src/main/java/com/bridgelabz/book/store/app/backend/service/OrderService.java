package com.bridgelabz.book.store.app.backend.service;

import com.bridgelabz.book.store.app.backend.dto.OrderDTO;
import com.bridgelabz.book.store.app.backend.dto.ResponseDTO;
import com.bridgelabz.book.store.app.backend.model.Book;
import com.bridgelabz.book.store.app.backend.model.Order;
import com.bridgelabz.book.store.app.backend.model.User;
import com.bridgelabz.book.store.app.backend.repository.OrderRepo;
import com.bridgelabz.book.store.app.backend.util.EmailService;
import com.bridgelabz.book.store.app.backend.util.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class OrderService {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private JWTToken jwtToken;

    @Autowired
    private EmailService emailService;

    public ResponseDTO placeOrder(String token, OrderDTO orderDTO) {
        User user = getUserFromToken(token);
        if (user != null) {
            Book book = bookService.getBookById(orderDTO.getBook());
            if (book != null && book.getBookQuantity() >= orderDTO.getQuantity()) {
                Order newOrder = new Order(user, book, orderDTO);
                orderRepo.save(newOrder);
                // Update book quantity and other necessary operations
                ResponseDTO responseDTO = new ResponseDTO("Order placed successfully", newOrder);
                emailService.sendEmail(newOrder.getUser().getEmailId(),"order placed","adderss"+orderDTO.getAddress());
                return responseDTO;
            } else {
                return new ResponseDTO("Invalid book or quantity", null);
            }
        } else {
            return new ResponseDTO("Unauthorized", null);
        }

    }
    public boolean cancelOrder(long userId, int orderId) {
        Order order = orderRepo.findByUserIdAndOrderId(userId, orderId);
        if (order != null) {
            if (!order.isCancel()) {
                order.setCancel(true);
                orderRepo.save(order);
                return true;
            }
        }
        return false;
    }

    private User getUserFromToken(String token) {
        try {
            long UserId = jwtToken.decodeToken(token);
            return userService.getUserById((int) UserId);
        } catch (Exception e) {
            return null; // Token decoding or user retrieval failed
        }
    }


    public List<Order> getAllOrdersForUser(long userId) {
        return orderRepo.findByUserIdAndCancel(userId, false);
    }

    public List<Order> getAllOrders(boolean cancel) {
        return orderRepo.findByCancel(cancel);
    }


}
