package com.velo.microserviceusermanagement.service;

import com.velo.microserviceusermanagement.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User findByUsername(String username);

    List<User> findUsers(List<Long> idList);
}
