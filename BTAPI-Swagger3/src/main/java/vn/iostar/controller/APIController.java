package vn.iostar.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.iostar.entity.Category;
import vn.iostar.model.Response;
import vn.iostar.service.CategoryService;
import vn.iostar.service.StorageService;

@RestController
@RequestMapping(path = "/api/category")
public class APIController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	StorageService storageService;

	@GetMapping
	public ResponseEntity<?> getAllCategory() {
		return new ResponseEntity<Response>(new Response(true, "Thành công", categoryService.findAll()), HttpStatus.OK);
	}

	@PostMapping(path = "/getCategory")
	public ResponseEntity<?> getCategory(@Validated @RequestParam("cateId") Integer id) {
		Optional<Category> category = categoryService.findById(id);
		if (category.isPresent()) {

			return new ResponseEntity<Response>(new Response(true, "Thành công", category.get()), HttpStatus.OK);
		} else {

			return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/addCategory")
	public ResponseEntity<?> addCategory(@RequestParam("cateName") String categoryName,
			@RequestParam("img") MultipartFile icon) {

		Optional<Category> optCategory = categoryService.findByCategoryName(categoryName);
		if (optCategory.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category đã tồn tại trong hệ thống");
		}

		Category category = new Category();
		try {
			if (!icon.isEmpty()) {
				String fileName = storageService.getStorageFilename(icon, UUID.randomUUID().toString());
				storageService.store(icon, fileName);
				category.setImg(fileName);
			}
			category.setCateName(categoryName);
			categoryService.save(category);

			return ResponseEntity.ok(new Response(true, "Thêm thành công", category));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi lưu file");
		}
	}

	@PutMapping(path = "/updateCategory")
	public ResponseEntity<?> updateCategory(@Validated @RequestParam("cateId") Integer categoryId,
			@Validated @RequestParam("cateName") String categoryName,
			@Validated @RequestParam("img") MultipartFile icon) {
		Optional<Category> optCategory = categoryService.findById(categoryId);
		if (optCategory.isEmpty()) {
			return new ResponseEntity<Response>(new Response(false, "Không	tìm thấy Category", null),
					HttpStatus.BAD_REQUEST);
		} else if (optCategory.isPresent()) {

			if (!icon.isEmpty()) {
				UUID uuid = UUID.randomUUID();
				String uuString = uuid.toString();
				
				optCategory.get().setImg(storageService.getStorageFilename(icon, uuString));
				storageService.store(icon, optCategory.get().getImg());
			}
			optCategory.get().setCateName(categoryName);
			categoryService.save(optCategory.get());
			// return ResponseEntity.ok().body(category);
			return new ResponseEntity<Response>(new Response(true, "Cập nhật Thành công", optCategory.get()),
					HttpStatus.OK);
		}
		return null;
	}

	@DeleteMapping(path = "/deleteCategory")
	public ResponseEntity<?> deleteCategory(@Validated @RequestParam("cateId") Integer categoryId) {
		Optional<Category> optCategory = categoryService.findById(categoryId);
		if (optCategory.isEmpty()) {
			return new ResponseEntity<Response>(new Response(false, "Không	tìm thấy Category", null),
					HttpStatus.BAD_REQUEST);
		} else if (optCategory.isPresent()) {
			categoryService.delete(optCategory.get());
			// return ResponseEntity.ok().body(category);
			return new ResponseEntity<Response>(new Response(true, "Xóa Thành công", optCategory.get()), HttpStatus.OK);
		}
		return null;
	}
}
