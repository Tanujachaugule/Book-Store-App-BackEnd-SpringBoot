package com.bridgelabz.book.store.app.backend.model;


import com.bridgelabz.book.store.app.backend.dto.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private LocalDate registeredDate = LocalDate.now();
    private LocalDate updatedDate = LocalDate.now();
    private String emailId;
    private String password;
    private boolean verify;
    private int otp;

    public User(UserDTO userDTO) {
        this.updateUser(userDTO);
    }

    public void updateUser(UserDTO userDTO) {
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.dob = userDTO.getDob();
        this.emailId = userDTO.getEmailId();
        this.password = userDTO.getPassword();


    }

}