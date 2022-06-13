package com.cybersoft.asimovapi.teachers.domain.repository;

import com.cybersoft.asimovapi.teachers.domain.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("select d from Teacher d where d.email = ?1")
    Teacher findByEmail(String email);
    List<Teacher> findByDirectorId(Long directorId);
    @Modifying
    @Query(value = "insert into teachers_courses(teacher_id, course_id) values (?1, ?2)", nativeQuery = true)
    @Transactional
    void registerCourseToTeacher(Long teacherId, Long courseId);
}
