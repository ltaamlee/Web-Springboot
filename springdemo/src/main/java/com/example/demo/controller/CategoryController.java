package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

	@Autowired
    private CategoryService categoryService;

    // ---------------- List + search ----------------
    @GetMapping()
    public String listCategories(
            Model model,
            @RequestParam(value = "cateName", required = false) String cateName) {

        if (cateName != null && !cateName.isEmpty()) {
            model.addAttribute("categories", categoryService.searchByName(cateName));
            model.addAttribute("cateName", cateName); // giữ giá trị search
        } else {
            model.addAttribute("categories", categoryService.findAll());
        }
        return "category/list";
    }

    // ---------------- Show form create ----------------
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        System.out.println("DEBUG: showCreateForm called");
        model.addAttribute("category", new Category());
        return "category/form";
    }


    // ---------------- Save (create or update) ----------------
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    // ---------------- Show form edit ----------------
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return "redirect:/admin/categories";
        }
        model.addAttribute("category", category);
        return "category/form";
    }

    // ---------------- Delete ----------------
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        categoryService.deleteById(id);
        return "redirect:/admin/categories";
    }
}
