package vn.iostar.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.iostar.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	List<Category> findByCateNameContaining(String keyword);
	
	Page<Category> findByCateNameContaining(String name, Pageable pageable);
}
