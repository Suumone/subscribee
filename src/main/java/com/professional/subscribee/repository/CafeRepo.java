package com.professional.subscribee.repository;

import com.professional.subscribee.model.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeRepo extends JpaRepository<Cafe, Long> {
}
