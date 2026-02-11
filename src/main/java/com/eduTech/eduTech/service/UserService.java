package com.eduTech.eduTech.service;

import com.eduTech.eduTech.dto.CreateStudentRequest;
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

    // ✅ Get all admins
    public List<User> getAllAdmins(){
        return userRepository.findByRole(Role.ADMIN);
    }

    // ✅ Create admin
    public User createAdmin(User user){
        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }

    // ✅ Create student
    public User createStudent(CreateStudentRequest request){

        User user = new User();

        user.setStudentName(request.getStudentName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setClassName(request.getClassName());
        user.setSchoolName(request.getSchoolName());
        user.setRole(Role.STUDENT);

        return userRepository.save(user);
    }

    // ✅ Delete user
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    // ✅ Get students
    public List<User> getAllStudents(){
        return userRepository.findByRole(Role.STUDENT);
    }

    // ✅ Delete student
    public void deleteStudent(Long id){
        userRepository.deleteById(id);
    }

    // ✅ Search students
    public List<User> searchStudentsByName(String name){
        return userRepository.findByStudentNameContainingIgnoreCaseAndRole(name, Role.STUDENT);
    }
}
