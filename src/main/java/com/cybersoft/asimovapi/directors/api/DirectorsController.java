package com.cybersoft.asimovapi.directors.api;

import com.cybersoft.asimovapi.directors.domain.service.DirectorService;
import com.cybersoft.asimovapi.directors.mapping.DirectorMapper;
import com.cybersoft.asimovapi.directors.resource.CreateDirectorResource;
import com.cybersoft.asimovapi.directors.resource.DirectorResource;
import com.cybersoft.asimovapi.directors.resource.UpdateDirectorResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/directors")
public class DirectorsController {

    @Autowired
    private DirectorService directorService;

    @Autowired
    private DirectorMapper mapper;

    @GetMapping
    public List<DirectorResource> getAllDirectors() { return mapper.modelListToResource(directorService.getAll()); }

    @GetMapping("{directorId}")
    public DirectorResource getDirectorById(@PathVariable("directorId") Long directorId) {
        return mapper.toResource(directorService.getById(directorId));
    }

    @PostMapping
    public DirectorResource createPost(@RequestBody CreateDirectorResource request) {

        return mapper.toResource(directorService.create(mapper.toModel(request)));
    }

    @PutMapping("{directorId}")
    public DirectorResource updatePost(@PathVariable Long directorId, @RequestBody UpdateDirectorResource request) {
        return mapper.toResource(directorService.update(directorId, mapper.toModel(request)));
    }

    @DeleteMapping("{directorId}")
    public ResponseEntity<?> deletePost(@PathVariable Long directorId) {
        return directorService.delete(directorId);
    }
}
