package com.cehn17.academy.user.service;

import com.cehn17.academy.user.dto.UserRequest;
import com.cehn17.academy.user.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findOneByUsername(String username);

}
