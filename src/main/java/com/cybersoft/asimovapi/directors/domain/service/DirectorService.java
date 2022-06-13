package com.cybersoft.asimovapi.directors.domain.service;

import com.cybersoft.asimovapi.directors.domain.model.entity.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DirectorService {
    List<Director> getAll();
    Page<Director> getAll(Pageable pageable);
    Director getById(Long directorId);
    Director create(Director director);
    Director update(Long directorId, Director director);
    ResponseEntity<?> delete(Long directorId);
}
