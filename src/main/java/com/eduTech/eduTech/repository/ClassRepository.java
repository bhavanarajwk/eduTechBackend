package com.eduTech.eduTech.repository;

import com.eduTech.eduTech.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {

    Optional<ClassEntity> findByClassName(String className);

}
