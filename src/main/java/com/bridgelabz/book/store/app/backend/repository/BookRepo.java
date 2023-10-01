package com.bridgelabz.book.store.app.backend.repository;

import com.bridgelabz.book.store.app.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Integer> {

}
