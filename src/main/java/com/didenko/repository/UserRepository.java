package com.didenko.repository;

import com.didenko.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    User save(User user);

//    User update(User user);

    void deleteById(Long id);

    void delete(User user);
}
