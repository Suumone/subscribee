package com.professional.subscribee.service;

import com.professional.subscribee.model.Cafe;
import com.professional.subscribee.repository.CafeRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CafeService {
    private final CafeRepo cafeRepo;

    @Transactional
    public Cafe createCafe(String name, String phone, String description) {
        return cafeRepo.save(Cafe.builder()
                .name(name)
                .phone(phone)
                .description(description)
                .build());
    }
}
