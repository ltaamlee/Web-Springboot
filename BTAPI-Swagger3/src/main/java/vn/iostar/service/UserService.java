package vn.iostar.service;

import java.util.Optional;

import vn.iostar.entity.User;


public interface UserService {
	Optional<User> login(String username, String password);

    Optional<User> findByUsername(String username);

	User save(User user);

    Optional<User> findById(String userId);
}
