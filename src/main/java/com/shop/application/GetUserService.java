package com.shop.application;

import com.shop.models.User;
import com.shop.models.UserId;
import com.shop.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetUserService {
    private final UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(UserId id) {
        return userRepository.findById(id)
                .orElseThrow();
    }
}
