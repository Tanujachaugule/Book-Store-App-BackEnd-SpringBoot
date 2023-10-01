package com.bridgelabz.book.store.app.backend.controller;


import com.bridgelabz.book.store.app.backend.dto.LoginDTO;
import com.bridgelabz.book.store.app.backend.dto.ResponseDTO;
import com.bridgelabz.book.store.app.backend.dto.UserDTO;
import com.bridgelabz.book.store.app.backend.model.User;
import com.bridgelabz.book.store.app.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        ResponseDTO users = userService.registerUser(userDTO);
        ResponseDTO responseDTO = new ResponseDTO(" registration successful", users);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    @PostMapping("/verifyotp")
    public ResponseEntity<ResponseDTO> verifyOtp(@RequestParam String emailId, @RequestParam Integer otp) {
        ResponseDTO response = userService.verifyOtp(emailId, otp);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login( @Valid @RequestBody LoginDTO loginDTO) {
        ResponseDTO response = userService.login(loginDTO);
        if (response.getData() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable long id, @Valid @RequestBody UserDTO userDTO) {
        User user = userService.updateUser((int) id, userDTO);
        ResponseDTO responseDTO = new ResponseDTO("Data updated Successfully", user);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable long id) {
        userService.deleteUser((int) id);
        ResponseDTO responseDTO = new ResponseDTO("Data deleted successfully", null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @DeleteMapping("/delete/all")
    public ResponseEntity<ResponseDTO> deleteAllUser() {
        userService.deleteAllUser();
        ResponseDTO responseDTO = new ResponseDTO("All data deleted successfully", null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable long id) {
        User user = userService.getUserById((int) id);
        ResponseDTO responseDTO;
        HttpStatus status;
        if (user != null) {
            responseDTO = new ResponseDTO("User found", user);
            status = HttpStatus.OK;
        } else {
            responseDTO = new ResponseDTO("User not found", null);
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(responseDTO, status);
    }
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAllUser() {
        List<User> user = userService.getAllUser();
        ResponseDTO responseDTO;
        HttpStatus status;
        if (!user.isEmpty()) {
            responseDTO = new ResponseDTO("user found", user);
            status = HttpStatus.OK;
        } else {
            responseDTO = new ResponseDTO("No user found", null);
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(responseDTO, status);
    }
}