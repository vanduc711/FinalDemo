package com.tutorial.finaldemo.service.impl;

import com.tutorial.finaldemo.entity.User;
import com.tutorial.finaldemo.reponsitory.UserReponsitory;
import com.tutorial.finaldemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final private UserReponsitory userReponsitory;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userReponsitory.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public Optional<User> getUserById(int id) {
        return Optional.empty();
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public void deleteUserById(int id) {

    }

    @Override
    public Optional<User> getUserByName(String name) {
        return Optional.empty();
    }
}
