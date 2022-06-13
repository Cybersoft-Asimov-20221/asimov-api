package com.cybersoft.asimovapi.teachers.api;

import com.cybersoft.asimovapi.teachers.resource.SaveTeacherResource;
import com.cybersoft.asimovapi.teachers.resource.TeacherResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybersoft.asimovapi.teachers.domain.service.TeacherService;
import com.cybersoft.asimovapi.teachers.mapping.teacherMapper;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class teachersController {

    private final TeacherService teacherService;

    private final teacherMapper mapper;

    public teachersController(TeacherService teacherService, teacherMapper mapper) {
        this.teacherService = teacherService;
        this.mapper = mapper;
    }

    @GetMapping("directors/{directorId}/teachers")
    public List<TeacherResource> getTeachersByDirectorId(@PathVariable("directorId") Long directorId){
        return mapper.modelListToResource(teacherService.getAllByDirectorId(directorId));
    }

    @PostMapping("teachers/{teacherId}/courses/{courseId}")
    public ResponseEntity<?> addCourseToTeacher(@PathVariable Long teacherId, Long courseId) {
        return teacherService.addCourseToTeacher(courseId, teacherId);
    }

    @PostMapping("directors/{directorId}/teachers")
    public TeacherResource createTeacher(@PathVariable("directorId")Long directorId, @RequestBody SaveTeacherResource resource){
        return mapper.toResource(teacherService.createTeacher(directorId, mapper.toModel(resource)));
    }

    @PutMapping("teachers/{teacherId}")
    public TeacherResource updateTeacher(@PathVariable("teacherId") Long teacherId, @RequestBody SaveTeacherResource resource){
        return mapper.toResource(teacherService.updateTeacher(teacherId, mapper.toModel(resource)));
    }

    @DeleteMapping("teachers/{teacherId}")
    public ResponseEntity<?> deleteTeacher(@PathVariable("teacherId") Long teacherId){
        return teacherService.deleteTeacher(teacherId);
    }
}
