package com.cybersoft.asimovapi.courses.service;

import com.cybersoft.asimovapi.courses.domain.model.entity.Course;
import com.cybersoft.asimovapi.courses.domain.persistence.CourseRepository;
import com.cybersoft.asimovapi.courses.domain.service.CourseService;
import com.cybersoft.asimovapi.shared.exception.ResourceNotFoundException;
import com.cybersoft.asimovapi.shared.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {

    private static final String ENTITY = "Course";

    private final CourseRepository courseRepository;

    private final Validator validator;

    public CourseServiceImpl(CourseRepository courseRepository, Validator validator) {
        this.courseRepository = courseRepository;
        this.validator = validator;
    }


    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public Page<Course> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public Course getById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, courseId));
    }

    @Override
    public Course create(Course course) {
        Set<ConstraintViolation<Course>> violations = validator.validate(course);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        var existingName = courseRepository.findByName(course.getName());
        if(existingName != null) {
            throw new ResourceValidationException("course is already registered");
        }

        return courseRepository.save(course);
    }

    @Override
    public Course update(Long courseId, Course request) {
        Set<ConstraintViolation<Course>> violations = validator.validate(request);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return courseRepository.findById(courseId).map(course ->
                courseRepository.save(
                        course.withName(request.getName())
                                .withDescription(request.getDescription())
                                .withState(request.getState()))
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, courseId));
    }

    @Override
    public ResponseEntity<?> delete(Long courseId) {
        return courseRepository.findById(courseId).map(course -> {
            courseRepository.delete(course);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, courseId));
    }
}
