package com.cybersoft.asimovapi.directors.service;

import com.cybersoft.asimovapi.directors.domain.model.entity.Director;
import com.cybersoft.asimovapi.directors.domain.persistence.DirectorRepository;
import com.cybersoft.asimovapi.directors.domain.service.DirectorService;
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
public class DirectorServiceImpl implements DirectorService {

    private static final String ENTITY = "Director";

    private final DirectorRepository directorRepository;

    private final Validator validator;

    public DirectorServiceImpl(DirectorRepository directorRepository, Validator validator) {
        this.directorRepository = directorRepository;
        this.validator = validator;
    }

    @Override
    public List<Director> getAll() {
        return directorRepository.findAll();
    }

    @Override
    public Page<Director> getAll(Pageable pageable) {
        return directorRepository.findAll(pageable);
    }

    @Override
    public Director getById(Long directorId) {
        //var er = directorRepository.findById(directorId);
        return directorRepository.findById(directorId)
                .orElseThrow( () -> new ResourceNotFoundException(ENTITY, directorId));
    }

    @Override
    public Director create(Director director) {
        Set<ConstraintViolation<Director>> violations = validator.validate(director);
        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        var existingEmail = directorRepository.findByEmail(director.getEmail());
        if(existingEmail != null) {
            throw new ResourceValidationException("email is already taken");
        }

        return directorRepository.save(director);
    }

    @Override
    public Director update(Long directorId, Director director) {
        Set<ConstraintViolation<Director>> violations = validator.validate(director);
        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return directorRepository.findById(directorId).map( data ->
                directorRepository.save(
                        data.withFirstName(director.getFirstName())
                                .withLastName(director.getLastName())
                                .withAge(director.getAge())
                                .withEmail(director.getEmail())
                                .withPhone(director.getPhone()))
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, directorId));
    }

    @Override
    public ResponseEntity<?> delete(Long directorId) {
        return directorRepository.findById(directorId).map(data -> {
            directorRepository.delete(data);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, directorId));
    }
}
