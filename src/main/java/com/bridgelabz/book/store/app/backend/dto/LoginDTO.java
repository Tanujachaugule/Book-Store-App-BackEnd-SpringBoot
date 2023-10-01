package com.bridgelabz.book.store.app.backend.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class LoginDTO {

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "email are not matching invalid")
    private String emailId;
    private String password;
}
