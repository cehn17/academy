package com.cehn17.academy.user.service.impl;


import com.cehn17.academy.user.dto.UserRequest;
import com.cehn17.academy.user.entity.User;
import com.cehn17.academy.user.repository.UserRepository;
import com.cehn17.academy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
