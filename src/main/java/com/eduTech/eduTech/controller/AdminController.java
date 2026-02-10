package com.eduTech.eduTech.controller;

import com.eduTech.eduTech.entity.User;
import com.eduTech.eduTech.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/list")
    public List<User> getAllAdmins(){
        return userService.getAllAdmins();
    }

    @PostMapping("/create-admin")
    public User createAdmin(@RequestBody User user){
        return userService.createAdmin(user);
    }

    @PostMapping("/create-student")
    public User createStudent(@RequestBody User user){
        return userService.createStudent(user);
    }

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @GetMapping("/students")
    public List<User> getAllStudents(){
        return userService.getAllStudents();
    }

    @DeleteMapping("/student/{id}")
    public void deleteStudent(@PathVariable Long id){
        userService.deleteStudent(id);
    }


}
