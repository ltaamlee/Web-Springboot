package vn.iostar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iostar.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<User> findById(String userId);

	Optional<User> findByUsername(String username);

	Optional<User> findByUsernameAndPassword(String username, String password);

}
