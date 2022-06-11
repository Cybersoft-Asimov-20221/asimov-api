package com.cybersoft.asimovapi.competences.api;

import com.cybersoft.asimovapi.competences.domain.service.CompetenceService;
import com.cybersoft.asimovapi.competences.mapping.CompetenceMapper;
import com.cybersoft.asimovapi.competences.resource.CompetenceResource;
import com.cybersoft.asimovapi.competences.resource.CreateCompetenceResource;
import com.cybersoft.asimovapi.competences.resource.UpdateCompetenceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/competences")
public class CompetencesController {

    @Autowired
    private CompetenceService competenceService;

    @Autowired
    private CompetenceMapper mapper;

    @GetMapping
    public List<CompetenceResource> getAllCompetences() {
        return mapper.modelListToResource(competenceService.getAll());
    }

    @GetMapping("{competenceId}")
    public CompetenceResource getCompetenceById(@PathVariable("competenceId") Long competenceId) {
        return mapper.toResource(competenceService.getById(competenceId));
    }

    @PostMapping
    public CompetenceResource createCompetence(@RequestBody CreateCompetenceResource request) {

        return mapper.toResource(competenceService.create(mapper.toModel(request)));
    }

    @PutMapping("{competenceId}")
    public CompetenceResource updateCompetence(@PathVariable Long competenceId, @RequestBody UpdateCompetenceResource request) {
        return mapper.toResource(competenceService.update(competenceId, mapper.toModel(request)));
    }

    @DeleteMapping("{competenceId}")
    public ResponseEntity<?> deleteCompetence(@PathVariable Long competenceId) {
        return competenceService.delete(competenceId);
    }

}
