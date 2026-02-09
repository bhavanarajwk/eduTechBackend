package com.eduTech.eduTech.controller;

import com.eduTech.eduTech.entity.User;
import com.eduTech.eduTech.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PostMapping("/create-student")
    public User createStudent(@RequestBody User user){
        return userService.createStudent(user);
    }
}
