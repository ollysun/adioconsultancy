package com.adioconsultancy.recruitment.repository;

import com.adioconsultancy.recruitment.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProfileRepository extends JpaRepository<Profile, Long> {
    Boolean existsByEmail(String email);

}
