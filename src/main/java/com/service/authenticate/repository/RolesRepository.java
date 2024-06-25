package com.service.authenticate.repository;

import com.service.authenticate.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles,String> {

    @Query("Select u from Roles u where u.name like ?1")
    Optional<Roles> findByName(String roleName);
}
