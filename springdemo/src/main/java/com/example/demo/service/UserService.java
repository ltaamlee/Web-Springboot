package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.User;

public interface UserService {

	List<User> findAll();

    User getUserById(Integer id); 

    User saveUser(User user);

    void deleteUser(Integer id);

    List<User> searchUsers(String keyword);
    
    User login(String userName, String password);

}
