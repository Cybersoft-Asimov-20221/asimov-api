package com.cybersoft.asimovapi.directors.domain.persistence;

import com.cybersoft.asimovapi.directors.domain.model.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

    @Query("select d from Director d where d.email = ?1")
    Director findByEmail(String email);
}
