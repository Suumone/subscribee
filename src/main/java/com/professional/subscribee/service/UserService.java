package com.professional.subscribee.service;

import com.professional.subscribee.model.User;
import com.professional.subscribee.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User getUser(Long id) {
        return userRepo.getById(id);
    }

    public void deleteUser(long userId) {
        userRepo.deleteById(userId);
    }

    public User createUser(String username, String password, String phone, String email) {
        return userRepo.save(new User(username, password, phone, email));
    }
}
