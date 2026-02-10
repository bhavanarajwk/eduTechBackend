package com.eduTech.eduTech.service;

import com.eduTech.eduTech.entity.Role;
import com.eduTech.eduTech.entity.User;
import com.eduTech.eduTech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    public List<User> getAllAdmins(){
        return userRepository.findByRole(Role.ADMIN);
    }

    public User createAdmin(User user){
        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }

    public User createStudent(User user){
        user.setRole(Role.STUDENT);
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public List<User> getAllStudents(){
        return userRepository.findByRole(Role.STUDENT);
    }

    public void deleteStudent(Long id){
        userRepository.deleteById(id);
    }

}
