package com.example.financeapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.financeapp.model.Status;
import com.example.financeapp.model.User;
import com.example.financeapp.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public User create(User user) {
        user.setStatus(Status.ACTIVE);
        return repo.save(user);
    }

    public List<User> getAll() {
        return repo.findAll();
    }
}
