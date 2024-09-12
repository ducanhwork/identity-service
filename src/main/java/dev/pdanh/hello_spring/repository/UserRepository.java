package dev.pdanh.hello_spring.repository;

import dev.pdanh.hello_spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findById(String id);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
