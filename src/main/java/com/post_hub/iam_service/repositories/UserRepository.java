package com.post_hub.iam_service.repositories;

import com.post_hub.iam_service.model.enteties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByIdAndDeletedFalse(Long id);
    Optional<User> findByEmailAndDeletedFalse(String email);
    Optional<User> findByEmail(String username);
    Optional<User> findByUsername(String username);
}
