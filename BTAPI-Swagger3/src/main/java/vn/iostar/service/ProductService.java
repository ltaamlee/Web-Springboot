package vn.iostar.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iostar.entity.Product;

public interface ProductService {

    // Lấy tất cả sản phẩm
    List<Product> findAll();

    // Lấy tất cả theo sắp xếp
    List<Product> findAll(Sort sort);

    // Lấy tất cả theo phân trang
    Page<Product> findAll(Pageable pageable);

    // Tìm theo ID
    Optional<Product> findById(Integer id);

    // Xóa entity
    void delete(Product product);

    // Tìm kiếm theo tên
    List<Product> findByProductNameContaining(String name);

    // Tìm kiếm theo tên có phân trang
    Page<Product> findByProductNameContaining(String name, Pageable pageable);

    // Đếm tổng số sản phẩm
    Integer count();

    // Tìm theo Example
    <S extends Product> Optional<S> findOne(Example<S> example);

    // Lấy tất cả theo danh sách ID
    List<Product> findAllById(Iterable<Integer> ids);

    // Tìm theo tên chính xác
    Optional<Product> findByProductName(String name);

    // Lưu hoặc cập nhật sản phẩm
    <S extends Product> S save(S entity);

    // Xóa theo ID
    void deleteById(Integer id);

	Optional<Product> findByCreateDate(Date createAt);
}
