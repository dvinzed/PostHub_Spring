package com.post_hub.iam_service.repositories;

import com.post_hub.iam_service.model.enteties.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(String name);
}
