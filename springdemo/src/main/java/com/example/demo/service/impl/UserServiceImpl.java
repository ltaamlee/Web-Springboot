package com.example.demo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<User> searchUsers(String keyword) {
		return userRepository.findByUserNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
	}
	
	@Override
	public User login(String userName, String password) {
	    User user = userRepository.findByUserName(userName);
	    System.out.println("DEBUG: user từ DB = " + user); // in ra đối tượng User
	    if (user != null) {
	        System.out.println("DEBUG: password DB = " + user.getPassword());
	    }
	    if (user != null && user.getPassword().equals(password)) {
	        System.out.println("DEBUG: login thành công!");
	        return user;
	    }
	    System.out.println("DEBUG: login thất bại!");
	    return null;
	}

}
