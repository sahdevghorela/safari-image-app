package com.learn.springboot.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

public interface UserRepository extends CrudRepository<User,Long>{

    User findByUsername(String username);
}
