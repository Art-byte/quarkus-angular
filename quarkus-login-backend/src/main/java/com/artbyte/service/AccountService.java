package com.artbyte.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.artbyte.model.User;
import com.artbyte.repository.HeroRepository;
import com.artbyte.repository.UserRepository;

import io.quarkus.elytron.security.common.BcryptUtil;

@Transactional
@ApplicationScoped
public class AccountService {

    @Inject
    HeroRepository heroRepository;

    @Inject
    UserRepository userRepository;

    public AccountService() {
    }

    public User userUpdate(User user) {
        User userToUpdate = findUserById(user.id);
        userToUpdate.setUsername(user.getUsername());
        userRepository.persist(userToUpdate);
        return userToUpdate;
    }

    public User deleteUserById(Long id) {
        User userDelete = findUserById(id);
        if (userDelete != null) {
            userRepository.delete(userDelete);
        }
        return userDelete;
    }

    public User addUser(User user) {
        user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
        userRepository.persist(user);
        return user;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll().list();
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
