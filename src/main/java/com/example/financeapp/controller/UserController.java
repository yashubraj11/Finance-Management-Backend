package com.example.financeapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.financeapp.model.User;
import com.example.financeapp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping
    public User create(@RequestBody User user) {
        return service.create(user);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }
}
