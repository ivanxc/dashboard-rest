package com.ivanxc.hse.dashboardrest.service;

import com.ivanxc.hse.dashboardrest.entity.User;
import com.ivanxc.hse.dashboardrest.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getByName(String name) {
        return userRepository.findByName(name);
    }
}
