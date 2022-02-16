package com.professional.subscribee.repository;

import com.professional.subscribee.model.ConfirmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmTokenRepo extends JpaRepository<ConfirmToken, Long> {
    void deleteByToken(String token);

    Optional<ConfirmToken> findByToken(String token);

}
