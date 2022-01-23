package com.professional.subscribee.repository;

import com.professional.subscribee.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    void deleteById(long id);
}
