package com.artbyte.repository;

import javax.enterprise.context.ApplicationScoped;

import com.artbyte.model.User;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User>{
    
    public UserRepository(){}

    public User findByUsername(String username){
        return find("username", username).firstResult();
    }
}
