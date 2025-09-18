package vn.iostar.service;

import java.util.List;
import java.util.Optional;

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

    // Lưu / cập nhật
    Category save(Category category);

    // Xóa theo ID
    void deleteById(Integer id);

    // Xóa entity
    void delete(Category category);

    // Xóa tất cả
    void deleteAll();

    // Tìm kiếm theo tên
    List<Category> findByNameContaining(String name);

    // Tìm kiếm theo tên có phân trang
    Page<Category> findByNameContaining(String name, Pageable pageable);
}
