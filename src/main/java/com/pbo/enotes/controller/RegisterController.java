package com.pbo.enotes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.pbo.enotes.entity.User;

import com.pbo.enotes.service.UserService;


@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showForms(Model model) {
        model.addAttribute("user", new User());
        return "register/signup"; // Nama file HTML Anda
    }

    @PostMapping("/register")
    ResponseEntity<String> registerUser(@ModelAttribute User user, BindingResult result, Model model) {

        if (result.hasErrors()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid form data");
        }

        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }

        // Tambahkan validasi password dan confirmPassword
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        }
        

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Simpan user ke database
        userService.saveUser(user);

        return ResponseEntity.ok("Registration successful");
    }



}