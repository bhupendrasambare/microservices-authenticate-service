package com.service.authenticate.repository;

import com.service.authenticate.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {

    @Query("Select u from Users u where u.email like ?1")
    Optional<Users> findByEmail(String email);
}
