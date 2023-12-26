package com.tutorial.finaldemo.reponsitory;


import com.tutorial.finaldemo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleReponsitory extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
