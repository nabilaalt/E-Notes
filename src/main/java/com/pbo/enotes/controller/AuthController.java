package com.pbo.enotes.controller;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
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
import org.springframework.web.bind.annotation.RequestMapping;

import com.pbo.enotes.entity.User;
import com.pbo.enotes.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLogin(Model model,HttpSession session) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser != null){
            return "redirect:/tasks";
        } 
        model.addAttribute("user", new User());
        return "login/signin"; // Nama file HTML Anda
    }

    @PostMapping("/auth")
    public ResponseEntity<String> loginUser(@ModelAttribute User user, BindingResult result, HttpSession session, HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid form data");
        }
    
        User existingUser = userService.findUserByEmail(user.getEmail());
        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            // Invalidate any existing session
            session.invalidate();
        
            // Create a new session
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("user", existingUser);
        
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials!");
        }
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Hapus sesi pengguna
        return "redirect:/login"; // Arahkan ke halaman login
    }
}
