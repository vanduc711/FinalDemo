package com.tutorial.finaldemo.service;


import com.tutorial.finaldemo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDetailsService userDetailsService();

    public List<User> getAllUsers();

    public Optional<User> getUserById(int id);

    public void saveUser(User user);

    public void deleteUserById(int id);

    public Optional<User> getUserByName(String name);

}
