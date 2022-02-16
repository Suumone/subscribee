package com.professional.subscribee.service;

import com.professional.subscribee.model.User;
import com.professional.subscribee.repository.RoleRepository;
import com.professional.subscribee.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

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
        userRepo.save(user);
        log.trace("User created(phone:{}, firstName:{}, lastName:{}, email:{})", phone, firstName, lastName, email);
        return user;
    }

    @Transactional
    public User createUser(String phone, String password) {
        User user = User.builder()
                .password(bCryptPasswordEncoder.encode(password))
                .phone(phone)
                .build();
        user.setRole(roleRepository.findByRoleName("USER"));
        userRepo.save(user);
        log.trace("User created(phone:{})", phone);
        return user;
    }

}
