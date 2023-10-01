package com.bridgelabz.book.store.app.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    // private Long id;
    @Pattern(regexp = "^[A-Z]{1}[A-Za-z\\s]{2,}$", message = " FirstName invalid")
    private String firstName;
    @Pattern(regexp = "^[A-Z]{1}[A-Za-z\\s]{2,}$", message = " lasttName invalid")
    private String lastName;

    private LocalDate dob;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "email are not matching invalid")
    private String emailId;
    // private boolean verify;
    private String password;
}
