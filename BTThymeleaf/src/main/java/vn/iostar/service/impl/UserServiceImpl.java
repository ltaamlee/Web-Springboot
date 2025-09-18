package vn.iostar.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iostar.entity.User;
import vn.iostar.repository.UserRepository;
import vn.iostar.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
    private UserRepository userRepository;


    @Override
    public Optional<User> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
