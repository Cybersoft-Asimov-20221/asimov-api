package com.cybersoft.asimovapi.security.domain.persistence;

import com.cybersoft.asimovapi.security.domain.model.entity.Role;
import com.cybersoft.asimovapi.security.domain.model.enumeration.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(Roles name);

    boolean existsByName(Roles name);
}