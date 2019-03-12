package com.adioconsultancy.recruitment.repository;

import com.adioconsultancy.recruitment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    User findUserById(Long id);

    Boolean existsByEmail(String email);
}
