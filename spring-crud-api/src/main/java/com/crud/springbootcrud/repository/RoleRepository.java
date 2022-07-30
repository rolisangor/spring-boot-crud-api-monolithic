package com.crud.springbootcrud.repository;

import com.crud.springbootcrud.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM role as r WHERE r.name = ?1 LIMIT 1", nativeQuery = true)
    Optional<Role> findByName(String name);

    void deleteByName(String name);

    boolean existsByName(String name);
}
