package com.eduTech.eduTech.service;

import com.eduTech.eduTech.dto.CreateSubjectRequest;
import com.eduTech.eduTech.dto.UploadMaterialRequest;
import com.eduTech.eduTech.entity.StudyMaterial;
import com.eduTech.eduTech.entity.Subject;
import com.eduTech.eduTech.repository.StudyMaterialRepository;
import com.eduTech.eduTech.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyllabusService {

    private final StudyMaterialRepository materialRepository;
    private final SubjectRepository subjectRepository;

    private final String uploadDir = "uploads/";

    // ================= SUBJECT =================

    public Subject addSubject(CreateSubjectRequest request) {
        Subject subject = new Subject();
        subject.setClassId(request.getClassId());
        subject.setSubjectName(request.getSubjectName());
        return subjectRepository.save(subject);
    }

    public List<Subject> getSubjectsByClass(Long classId) {
        return subjectRepository.findByClassId(classId);
    }

    // ================= MATERIAL =================

    public StudyMaterial uploadMaterial(UploadMaterialRequest request) {

        try {

            MultipartFile file = request.getFile();

            if (file == null || file.isEmpty()) {
                throw new RuntimeException("File is empty");
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);

            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            StudyMaterial material = new StudyMaterial();
            material.setClassId(request.getClassId());
            material.setSubjectId(request.getSubjectId());
            material.setTitle(request.getMaterialName());

            material.setFileName(fileName);
            material.setFileType(file.getContentType());
            material.setFilePath(filePath.toString());
            material.setUploadDate(LocalDateTime.now());

            return materialRepository.save(material);

        } catch (Exception e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    public List<StudyMaterial> getMaterialsByClass(Long classId) {
        return materialRepository.findByClassId(classId);
    }

    public StudyMaterial updateMaterial(Long id, UploadMaterialRequest request) {

        try {
            StudyMaterial material = materialRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Material not found"));

            MultipartFile file = request.getFile();

            if (file != null && !file.isEmpty()) {

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);

                Files.createDirectories(filePath.getParent());
                Files.write(filePath, file.getBytes());

                material.setFileName(fileName);
                material.setFileType(file.getContentType());
                material.setFilePath(filePath.toString());
            }

            material.setClassId(request.getClassId());
            material.setSubjectId(request.getSubjectId());
            material.setTitle(request.getMaterialName());

            return materialRepository.save(material);

        } catch (Exception e) {
            throw new RuntimeException("Update failed: " + e.getMessage());
        }
    }

    public void deleteMaterial(Long id) {

        StudyMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        try {
            Path path = Paths.get(material.getFilePath());
            Files.deleteIfExists(path);
        } catch (Exception e) {
            System.out.println("File delete failed");
        }

        materialRepository.deleteById(id);
    }

    public List<StudyMaterial> searchMaterial(String keyword) {
        return materialRepository.findByTitleContainingIgnoreCase(keyword);
    }
}
