package com.eduTech.eduTech.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentName;

    @Column(unique = true)
    private String email;

    private String password;

    private String className;

    private String schoolName;

    // ⭐ VERY IMPORTANT PART ⭐
    @Enumerated(EnumType.STRING)
    private Role role;

    public String getStudentClass() {
        return null;
    }
}
