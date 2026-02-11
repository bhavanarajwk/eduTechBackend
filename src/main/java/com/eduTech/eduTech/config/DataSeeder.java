package com.eduTech.eduTech.config;

import com.eduTech.eduTech.entity.ClassEntity;
import com.eduTech.eduTech.entity.QuizOption;
import com.eduTech.eduTech.entity.QuizQuestion;
import com.eduTech.eduTech.entity.StudyMaterial;
import com.eduTech.eduTech.entity.Subject;
import com.eduTech.eduTech.entity.User;
import com.eduTech.eduTech.repository.ClassRepository;
import com.eduTech.eduTech.repository.QuizOptionRepository;
import com.eduTech.eduTech.repository.QuizQuestionRepository;
import com.eduTech.eduTech.repository.StudyMaterialRepository;
import com.eduTech.eduTech.repository.SubjectRepository;
import com.eduTech.eduTech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final StudyMaterialRepository materialRepository;
    private final SubjectRepository subjectRepository;
    private final QuizQuestionRepository questionRepository;
    private final QuizOptionRepository optionRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 [DataSeeder] Checking for data consistency...");

        String studentEmail = "bhavana@school.com";
        Optional<User> studentOpt = userRepository.findByEmail(studentEmail);

        if (studentOpt.isPresent()) {
            User student = studentOpt.get();
            String className = student.getStudentClass();

            if (className != null && !className.isEmpty()) {
                System.out.println("✅ Found student: " + studentEmail + " with class: " + className);

                // 1. Check/Create Class
                ClassEntity classEntity = classRepository.findByClassName(className)
                        .orElseGet(() -> {
                            System.out.println("⚠️ Class '" + className + "' missing. Creating...");
                            ClassEntity newClass = new ClassEntity();
                            newClass.setClassName(className);
                            return classRepository.save(newClass);
                        });
                System.out.println("ℹ️ Class ID: " + classEntity.getId());

                // 2. Ensure "Mathematics" Subject Exists
                Subject mathSubject = subjectRepository.findByClassId(classEntity.getId()).stream()
                        .filter(s -> "Mathematics".equalsIgnoreCase(s.getSubjectName()))
                        .findFirst()
                        .orElseGet(() -> {
                            System.out.println("⚠️ Subject 'Mathematics' missing. Creating...");
                            Subject s = new Subject();
                            s.setSubjectName("Mathematics");
                            s.setClassId(classEntity.getId());
                            return subjectRepository.save(s);
                        });
                System.out.println("📘 Subject 'Mathematics' ID: " + mathSubject.getId());

                // 3. Ensure "Algebra Basics" Material Exists for Math
                if (materialRepository.searchInClass(classEntity.getId(), "Algebra").isEmpty()) {
                    System.out.println("⚠️ Material 'Algebra Basics' missing. Creating...");
                    StudyMaterial material = new StudyMaterial();
                    material.setTitle("Algebra Basics");
                    material.setClassId(classEntity.getId());
                    material.setSubjectId(mathSubject.getId());
                    material.setFileName("algebra.pdf");
                    material.setFileType("application/pdf");
                    material.setFilePath("/uploads/algebra.pdf");
                    material.setUploadDate(LocalDateTime.now());
                    materialRepository.save(material);
                    System.out.println("✅ Created 'Algebra Basics' linked to Math ID: " + mathSubject.getId());
                }

                // 4. Ensure "Science" Subject Exists
                Subject scienceSubject = subjectRepository.findByClassId(classEntity.getId()).stream()
                        .filter(s -> "Science".equalsIgnoreCase(s.getSubjectName()))
                        .findFirst()
                        .orElseGet(() -> {
                            System.out.println("⚠️ Subject 'Science' missing. Creating...");
                            Subject s = new Subject();
                            s.setSubjectName("Science");
                            s.setClassId(classEntity.getId());
                            return subjectRepository.save(s);
                        });
                System.out.println("📗 Subject 'Science' ID: " + scienceSubject.getId());

                // 5. Ensure "Physics Intro" Material Exists for Science
                if (materialRepository.searchInClass(classEntity.getId(), "Physics").isEmpty()) {
                    System.out.println("⚠️ Material 'Physics Intro' missing. Creating...");
                    StudyMaterial physics = new StudyMaterial();
                    physics.setTitle("Physics Intro");
                    physics.setClassId(classEntity.getId());
                    physics.setSubjectId(scienceSubject.getId());
                    physics.setFileName("physics.pdf");
                    physics.setFileType("application/pdf");
                    physics.setFilePath("/uploads/physics.pdf");
                    physics.setUploadDate(LocalDateTime.now());
                    materialRepository.save(physics);
                    System.out.println("✅ Created 'Physics Intro' linked to Science ID: " + scienceSubject.getId());
                }

                // 6. Ensure Sample Quiz Exists
                if (questionRepository.findByClassEntityId(classEntity.getId()).isEmpty()) {
                    System.out.println("⚠️ No Quiz Questions found. Creating 'Math Quiz'...");

                    QuizQuestion question = new QuizQuestion();
                    question.setQuestionText("What is 2 + 2?");
                    question.setClassEntity(classEntity);
                    question.setSubject(mathSubject);
                    question.setCreatedAt(LocalDateTime.now());
                    QuizQuestion savedQ = questionRepository.save(question);
                    System.out.println("✅ Created Question: " + savedQ.getQuestionText() + " for Subject: Math");

                    // Options
                    QuizOption opt1 = new QuizOption();
                    opt1.setQuestion(savedQ);
                    opt1.setOptionText("3");
                    opt1.setIsCorrect(false);
                    optionRepository.save(opt1);

                    QuizOption opt2 = new QuizOption();
                    opt2.setQuestion(savedQ);
                    opt2.setOptionText("4");
                    opt2.setIsCorrect(true);
                    optionRepository.save(opt2);

                    QuizOption opt3 = new QuizOption();
                    opt3.setQuestion(savedQ);
                    opt3.setOptionText("5");
                    opt3.setIsCorrect(false);
                    optionRepository.save(opt3);

                    System.out.println("✅ Added options to question ID: " + savedQ.getId());
                }

            } else {
                System.out.println("⚠️ Student found but no class assigned.");
            }
        } else {
            System.out.println("ℹ️ Student '" + studentEmail + "' not found. Skipping data seed.");
        }
    }
}
