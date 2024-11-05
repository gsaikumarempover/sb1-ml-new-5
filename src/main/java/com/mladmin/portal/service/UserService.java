package com.mladmin.portal.service;

import com.mladmin.portal.entity.User;
import com.mladmin.portal.repository.UserRepository;
import com.mladmin.portal.dto.UserUpdateInput;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Cacheable(value = "users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Cacheable(value = "users", key = "#username")
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    @CacheEvict(value = "users", key = "#id")
    public User updateUser(Long id, UserUpdateInput input) {
        User user = getUserById(id);
        
        if (input.getUsername() != null) {
            user.setUsername(input.getUsername());
        }
        if (input.getEmail() != null) {
            user.setEmail(input.getEmail());
        }

        return userRepository.save(user);
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public Boolean deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
        return true;
    }
}