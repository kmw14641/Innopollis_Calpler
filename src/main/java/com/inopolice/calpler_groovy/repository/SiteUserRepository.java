package com.inopolice.calpler_groovy.repository;

import com.inopolice.calpler_groovy.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteUserRepository extends JpaRepository<SiteUser, Integer> {
    Optional<SiteUser> findByUsername(String username);
}
