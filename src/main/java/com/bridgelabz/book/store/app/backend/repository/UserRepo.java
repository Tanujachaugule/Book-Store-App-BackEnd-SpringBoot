package com.bridgelabz.book.store.app.backend.repository;

import com.bridgelabz.book.store.app.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {


    User getUserByEmailId(String emailId);


    User findByEmailId(String emailId);
}
