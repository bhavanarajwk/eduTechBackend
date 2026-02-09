package com.eduTech.eduTech.service;

import com.eduTech.eduTech.entity.User;
import com.eduTech.eduTech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.eduTech.eduTech.entity.Role;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createStudent(User user){

        user.setRole(Role.STUDENT);
        // Force role

        return userRepository.save(user);
    }
}
