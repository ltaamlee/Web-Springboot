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

import vn.iostar.entity.Category;
import vn.iostar.service.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// ----- LIST CATEGORIES -----
	@GetMapping("/list")
	public String listCategories(Model model, @RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Category> categoryPage;

		if (keyword.isEmpty()) {
			categoryPage = categoryService.findAll(pageable);
		} else {
			categoryPage = categoryService.findByNameContaining(keyword, pageable);
		}

		model.addAttribute("categories", categoryPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", categoryPage.getTotalPages());
		model.addAttribute("keyword", keyword);

		return "admin/categories/list"; 
	}

	// ----- ADD CATEGORY FORM -----
	@GetMapping("/add")
	public String addCategoryForm(Model model) {
		Category category = new Category();
		model.addAttribute("category", category);
		return "AddOrEdit"; 
	}

	// ----- EDIT CATEGORY FORM -----
	@GetMapping("/edit/{id}")
	public String editCategoryForm(@PathVariable("id")Integer id, Model model) {
		Category category = categoryService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
		model.addAttribute("category", category);
		return "AddOrEdit";
	}

	// ----- SAVE OR UPDATE CATEGORY -----
	@PostMapping("/saveOrUpdate")
	public String saveOrUpdateCategory(@ModelAttribute("category") Category category) {
		categoryService.save(category);
		return "redirect:/admin/categories";
	}

	// ----- DELETE CATEGORY -----
	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable("id") Integer id) {
		categoryService.deleteById(id);
		return "redirect:/admin/categories";
	}
}