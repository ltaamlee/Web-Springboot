package vn.iostar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.iostar.entity.User;

public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findByUsernameAndPassword(String username, String password);
}
