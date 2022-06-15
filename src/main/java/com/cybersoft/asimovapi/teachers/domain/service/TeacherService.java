package com.cybersoft.asimovapi.teachers.domain.service;

import com.cybersoft.asimovapi.teachers.domain.model.Teacher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherService {
    List<Teacher> getAllByDirectorId(Long directorId);
    Teacher getById(Long id);

    public ResponseEntity<?> addCourseToTeacher(Long courseId, Long teacherId);

    Teacher createTeacher(Long directorId, Teacher teacher);

    Teacher updateTeacher(Long teacherId, Teacher teacher);

    ResponseEntity<?> deleteTeacher(Long teacherId);
}

