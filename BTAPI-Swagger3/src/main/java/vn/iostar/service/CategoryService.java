package vn.iostar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iostar.entity.Category;

public interface CategoryService {

	// Lấy tất cả
	List<Category> findAll();

	// Lấy tất cả theo sắp xếp
	List<Category> findAll(Sort sort);

	// Lấy tất cả theo phân trang
	Page<Category> findAll(Pageable pageable);

	// Tìm theo ID
	Optional<Category> findById(Integer id);

	// Xóa entity
	void delete(Category category);


	// Tìm kiếm theo tên
	List<Category> findByCateNameContaining(String name);

	// Tìm kiếm theo tên có phân trang
	Page<Category> findByCateNameContaining(String name, Pageable pageable);

	Integer count();

	<S extends Category> Optional<S> findOne(Example<S> example);

	List<Category> findAllById(Iterable<Integer> ids);

	Optional<Category> findByCategoryName(String name);

	<S extends Category> S save(S entity);

	Page<Category> findByCategoryNameContaining(String name, Pageable pageable);

	List<Category> findByCategoryNameContaining(String name);

	void deleteById(Integer id);
}
