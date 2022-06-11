package com.cybersoft.asimovapi.courses.api;

import com.cybersoft.asimovapi.courses.domain.service.CourseService;
import com.cybersoft.asimovapi.courses.mapping.CourseMapper;
import com.cybersoft.asimovapi.courses.resource.CourseResource;
import com.cybersoft.asimovapi.courses.resource.CreateCourseResource;
import com.cybersoft.asimovapi.courses.resource.UpdateCourseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/courses")
public class CoursesController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMapper mapper;

    @GetMapping
    public List<CourseResource> getAllCourses() {
        return mapper.modelListToResource(courseService.getAll());
    }

    @GetMapping("{courseId}")
    public CourseResource getCourseById(@PathVariable("courseId") Long courseId) {
        return mapper.toResource(courseService.getById(courseId));
    }

    @PostMapping
    public CourseResource createCourse(@RequestBody CreateCourseResource request) {

        return mapper.toResource(courseService.create(mapper.toModel(request)));
    }

    @PutMapping("{courseId}")
    public CourseResource updateCourse(@PathVariable Long courseId, @RequestBody UpdateCourseResource request) {
        return mapper.toResource(courseService.update(courseId, mapper.toModel(request)));
    }

    @DeleteMapping("{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        return courseService.delete(courseId);
    }
}
