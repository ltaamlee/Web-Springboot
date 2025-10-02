package vn.iostar.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import vn.iostar.dto.CategoryDTO;
import vn.iostar.entity.Category;
import vn.iostar.service.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    private final String UPLOAD_DIR = "D:/2025-2026/HK1/WEBDEV/LTW/springimg/";

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String listCategories(Model model,
                                 @RequestParam(defaultValue = "") String keyword,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size) {

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
        model.addAttribute("categoryDTO", new CategoryDTO());
        model.addAttribute("isEdit", false);
        return "admin/categories/AddOrEdit";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable("id") Integer id, Model model) {
        Category category = categoryService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        CategoryDTO dto = new CategoryDTO(category.getCateId(), category.getCateName(), category.getImg());
        model.addAttribute("categoryDTO", dto);
        model.addAttribute("isEdit", true);
        return "admin/categories/AddOrEdit";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdateCategory(@Valid @ModelAttribute("categoryDTO") CategoryDTO dto,
                                       BindingResult result,
                                       @RequestParam("file") MultipartFile file,
                                       Model model) {

        if (result.hasErrors()) {
            model.addAttribute("isEdit", dto.getCateId() != null);
            return "admin/categories/AddOrEdit";
        }

        try {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath);
                dto.setImg("/uploads/" + fileName);
            }

            Category category = new Category();
            category.setCateId(dto.getCateId());
            category.setCateName(dto.getCateName());
            category.setImg(dto.getImg());

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
