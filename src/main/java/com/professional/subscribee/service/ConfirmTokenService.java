package com.professional.subscribee.service;

import com.professional.subscribee.model.ConfirmToken;
import com.professional.subscribee.model.User;
import com.professional.subscribee.repository.ConfirmTokenRepo;
import com.professional.subscribee.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class ConfirmTokenService {

    private final ConfirmTokenRepo confirmTokenRepo;
    private final UserRepo userRepo;

    public ConfirmToken generateConfirmToken(User user) {
        ConfirmToken confirmToken = ConfirmToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .build();
        return confirmTokenRepo.save(confirmToken);
    }

    public void verifyByToken(String token) {
        ConfirmToken confirmToken = confirmTokenRepo.findByToken(token).orElseThrow(() -> new IllegalArgumentException("Invalid token"));
        enableUser(confirmToken);
    }

    private void enableUser(ConfirmToken confirmToken) {
        User user = confirmToken.getUser();
        userRepo.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setConfirmed(true);
        userRepo.save(user);
    }

    public void deleteConfirmToken(String token) {
        confirmTokenRepo.deleteByToken(token);
    }

}
