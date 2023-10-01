package com.bridgelabz.book.store.app.backend.service;

import com.bridgelabz.book.store.app.backend.dto.BookDTO;
import com.bridgelabz.book.store.app.backend.dto.ResponseDTO;
import com.bridgelabz.book.store.app.backend.exception.BookCustomException;
import com.bridgelabz.book.store.app.backend.exception.UserCustomException;
import com.bridgelabz.book.store.app.backend.model.Book;
import com.bridgelabz.book.store.app.backend.repository.BookRepo;
import com.bridgelabz.book.store.app.backend.util.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class BookService {

    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private JWTToken jwtToken;

    public ResponseDTO saveBook(BookDTO bookDTO) {
        Book bookData = new Book(bookDTO);
        bookRepo.save(bookData);
        return new ResponseDTO( "books details",bookData);
    }

    public Book updateBook(int bookId, BookDTO bookDTO) {
        Book bookData = getBookById(bookId);
        if (bookData == null) {
            throw new IllegalArgumentException("User not found with ID: " + bookId);
        }
        bookData.updateBook(bookDTO);
        return bookRepo.save(bookData);
    }

    public void deleteBook(int bookId) {
        Book bookData = getBookById(bookId);
        if (bookData == null) {
            throw new IllegalArgumentException("User not found with ID: " + bookId);
        }
        bookRepo.deleteById(bookId);
    }

    public Book getBookById(int bookId) {
        return bookRepo.findById(bookId).orElseThrow(() -> new UserCustomException("user with id: " + bookId + "not present"));
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }


    public Book changeBookQuantity(String token, int bookId, int bookQuantity) {
        long id = JWTToken.decodeToken(token);
        Book bookData = getBookById(bookId);
        if (bookData == null) {
            throw new BookCustomException("Book not found with ID: " + id);
        } else {
            bookData.setBookQuantity(bookQuantity);
            bookRepo.save(bookData);
            return bookData;
        }
    }

    public Book changeBookPrice(String token, int bookId, long price) {
        long id = JWTToken.decodeToken(token);
        Book bookData = getBookById(bookId);
        if (bookData == null) {
            throw new BookCustomException("Book not found with ID: " + bookId +id);
        } else {
            bookData.setBookPrice(price);
            bookRepo.save(bookData);
            return bookData;
        }

    }
}