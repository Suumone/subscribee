package com.professional.subscribee.service;

import com.professional.subscribee.model.User;
import com.professional.subscribee.repository.RoleRepository;
import com.professional.subscribee.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepo.getById(id);
    }

    @Transactional
    public void deleteUser(long userId) {
        userRepo.deleteById(userId);
    }

    @Transactional
    public User createUser(String firstName, String lastName, String password, String phone, String email) {
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(bCryptPasswordEncoder.encode(password))
                .phone(phone)
                .email(email)
                .build();
        user.setRole(roleRepository.findByRoleName("USER"));
        return userRepo.save(user);
    }

    @Transactional
    public User createUser(String phone, String password) {
        User user = User.builder()
                .password(bCryptPasswordEncoder.encode(password))
                .phone(phone)
                .build();
        user.setRole(roleRepository.findByRoleName("USER"));
        return userRepo.save(user);
    }

    public User findByPhone(String phone) {
        return userRepo.findByPhone(phone);
    }
}
