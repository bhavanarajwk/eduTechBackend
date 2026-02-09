package com.eduTech.eduTech.controller;

import com.eduTech.eduTech.dto.LoginRequest;
import com.eduTech.eduTech.entity.User;
import com.eduTech.eduTech.repository.UserRepository;
import com.eduTech.eduTech.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!user.getPassword().equals(request.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateToken(user);
    }
}
