package com.cybersoft.asimovapi.teachers.service;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;


import com.cybersoft.asimovapi.courses.domain.model.entity.Course;
import com.cybersoft.asimovapi.courses.domain.persistence.CourseRepository;
import com.cybersoft.asimovapi.directors.domain.persistence.DirectorRepository;
import com.cybersoft.asimovapi.shared.exception.ResourceNotFoundException;
import com.cybersoft.asimovapi.shared.exception.ResourceValidationException;
import com.cybersoft.asimovapi.teachers.domain.model.Teacher;
import com.cybersoft.asimovapi.teachers.domain.repository.TeacherRepository;
import com.cybersoft.asimovapi.teachers.domain.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    private static final String ENTITY = "Teacher";
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private Validator validator;

    @Override
    public List<Teacher> getAllByDirectorId(Long directorId) {
        var existingDirector = directorRepository.findById(directorId);

        if(existingDirector.isEmpty())
            throw new ResourceNotFoundException("Director", directorId);

        return teacherRepository.findByDirectorId(directorId);
    }

    @Override
    public ResponseEntity<?> addCourseToTeacher(Long courseId, Long teacherId) {
        if (courseRepository.findById(courseId).isEmpty())
            throw new ResourceNotFoundException("course not found");
        if (teacherRepository.findById(teacherId).isEmpty())
            throw new ResourceNotFoundException("teacher not found");

        List<Course> courses = courseRepository.getAllCoursesByTeacherId(teacherId);
        if (courses.contains(courseRepository.findById(courseId).get()))
            throw new ResourceNotFoundException("course is already added for this teacher");

        teacherRepository.registerCourseToTeacher(teacherId, courseId);

        return ResponseEntity.ok("course registered for teacher");
    }

    @Override
    public Teacher createTeacher(Long directorId, Teacher request) {
        Set<ConstraintViolation<Teacher>> violations = validator.validate(request);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        var existingEmail = teacherRepository.findByEmail(request.getEmail());
        if(existingEmail != null) {
            throw new ResourceValidationException("email is already taken");
        }

        return directorRepository.findById(directorId).map(director -> {
            request.setDirector(director);
            return teacherRepository.save(request);
        }).orElseThrow(() -> new ResourceNotFoundException("Director", directorId));
    }

    @Override
    public Teacher updateTeacher(Long teacherId, Teacher request) {
        Set<ConstraintViolation<Teacher>> violations = validator.validate(request);
        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return teacherRepository.findById(teacherId).map(
                teacherP -> teacherRepository.save(
                        teacherP.withFirst_name(request.getFirst_name())
                                .withLast_name(request.getLast_name())
                                .withAge(request.getAge())
                                .withPoint(request.getPoint())
                                .withEmail(request.getEmail())
                                .withPhone(request.getPhone()))
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, teacherId));
    }

    @Override
    public ResponseEntity<?> deleteTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId).map( teacherP -> {
            teacherRepository.delete(teacherP);
            return ResponseEntity.ok().build();
        })
        .orElseThrow(() -> new ResourceNotFoundException(ENTITY, teacherId));
    }
}
