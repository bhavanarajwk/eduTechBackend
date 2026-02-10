package com.eduTech.eduTech.repository;

import com.eduTech.eduTech.entity.Role;
import com.eduTech.eduTech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);
}
