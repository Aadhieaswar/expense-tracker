package com.aadhie.expensetracker.controllers.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(HttpServletRequest request) {
        String uid = (String) request.getAttribute("uid");
        String email = (String) request.getAttribute("email");

        if (uid == null || email == null)
            return ResponseEntity.status(401).body("Unauthorized");

        return ResponseEntity.ok().build();
    }
}
