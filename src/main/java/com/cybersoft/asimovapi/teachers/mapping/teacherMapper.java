package com.cybersoft.asimovapi.teachers.mapping;

import com.cybersoft.asimovapi.teachers.domain.model.Teacher;
import com.cybersoft.asimovapi.teachers.resource.SaveTeacherResource;
import com.cybersoft.asimovapi.teachers.resource.TeacherResource;
import org.springframework.beans.factory.annotation.Autowired;
import com.cybersoft.asimovapi.shared.mapping.EnhancedModelMapper;
import java.util.List;
public class teacherMapper {
    
    @Autowired
    EnhancedModelMapper mapper;

    public TeacherResource toResource(Teacher model){
        return mapper.map(model,TeacherResource.class);
    }

    public List<TeacherResource> modelListToResource(List<Teacher> modelList){
        return mapper.mapList(modelList, TeacherResource.class);
    }

    public Teacher toModel(SaveTeacherResource resource) {
        return mapper.map(resource, Teacher.class);
    }
}
