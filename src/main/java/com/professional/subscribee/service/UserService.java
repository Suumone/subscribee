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

    //@Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User getUser(Long id) {
        return userRepo.getById(id);
    }

    //@Transactional
    public void createUser(User user) {
        userRepo.save(user);
    }

    //@Transactional
    public void updateUser(User user) {
        userRepo.save(user);
    }

    //@Transactional
    public void deleteUser(long userId) {
        userRepo.deleteById(userId);
    }

    public User createUser(String username, String password, String phone, String email) {
        return userRepo.save(new User(username, password, phone, email));
    }
}
