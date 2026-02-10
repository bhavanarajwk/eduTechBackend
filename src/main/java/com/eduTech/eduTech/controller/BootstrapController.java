package com.eduTech.eduTech.controller;

import com.eduTech.eduTech.entity.Role;
import com.eduTech.eduTech.entity.User;
import com.eduTech.eduTech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BootstrapController {

    private final UserRepository userRepository;

    @PostMapping("/signup")
    public User signup(@RequestBody User user){

        user.setRole(Role.ADMIN);

        return userRepository.save(user);
    }
}
