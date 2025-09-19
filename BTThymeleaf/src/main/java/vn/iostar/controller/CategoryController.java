package vn.iostar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.iostar.entity.Category;
import vn.iostar.service.CategoryService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

	private final String UPLOAD_DIR = "D:/2025-2026/HK1/WEBDEV/LTW/springimg/";

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/list")
	public String listCategories(Model model, @RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Category> categoryPage = keyword.isEmpty() ? categoryService.findAll(pageable)
				: categoryService.findByNameContaining(keyword, pageable);

		model.addAttribute("categories", categoryPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", categoryPage.getTotalPages());
		model.addAttribute("keyword", keyword);

		return "admin/categories/list";
	}

	@GetMapping("/add")
	public String addCategoryForm(Model model) {
		Category category = new Category();
		model.addAttribute("category", category);
		model.addAttribute("isEdit", false);
		return "admin/categories/AddOrEdit";
	}

	@GetMapping("/edit/{id}")
	public String editCategoryForm(@PathVariable("id") Integer id, Model model) {
		Category category = categoryService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
		model.addAttribute("category", category);
		model.addAttribute("isEdit", true);
		return "admin/categories/AddOrEdit";
	}

	@PostMapping("/saveOrUpdate")
	public String saveOrUpdateCategory(@ModelAttribute("category") Category category,
			@RequestParam("file") MultipartFile file) {

		try {
			if (!file.isEmpty()) {
				String fileName = file.getOriginalFilename();

				Path uploadPath = Paths.get(UPLOAD_DIR);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				Path filePath = uploadPath.resolve(fileName);
				Files.copy(file.getInputStream(), filePath);

				category.setImg("/uploads/" + fileName);
			}

			categoryService.save(category);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/admin/categories/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable("id") Integer id) {
		Category category = categoryService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));

		if (category.getImg() != null && !category.getImg().isEmpty()) {
			String filePath = UPLOAD_DIR + category.getImg().substring("/uploads/".length());
			Path path = Paths.get(filePath);
			try {
				Files.deleteIfExists(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		categoryService.deleteById(id);
		if (categoryService.findAll().isEmpty()) {
			categoryService.resetIdentity();
		}
		return "redirect:/admin/categories/list";
	}

}