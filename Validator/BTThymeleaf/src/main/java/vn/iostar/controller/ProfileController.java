package vn.iostar.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import vn.iostar.entity.User;
import vn.iostar.service.UserService;

@Controller
@RequestMapping("/admin/profile")
public class ProfileController {

	private final String UPLOAD_DIR = "D:/2025-2026/HK1/WEBDEV/LTW/springimg/profile/";

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String showProfile(HttpSession session, Model model) {
		String userId = (String) session.getAttribute("userId"); 
	    if (userId == null) {
	        return "redirect:/login";
	    }

	    return userService.findById(userId)
	            .map(user -> {
	                model.addAttribute("user", user);
	                return "admin/profile";
	            })
	            .orElse("redirect:/login");
	}
	

	@PostMapping("/save")
	public String saveProfile(User user, @RequestParam("img") MultipartFile file, HttpSession session, Model model) {

		String userId = (String) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/login";
		}

		User currentUser = userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		try {
			if (!file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				Path uploadPath = Paths.get(UPLOAD_DIR);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(file.getInputStream(), filePath);

				currentUser.setImg("/uploads/profile/" + fileName);
			}


			userService.save(currentUser);
			model.addAttribute("message", "Cập nhật profile thành công!");

		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("message", "Lỗi khi upload ảnh!");
		}

		model.addAttribute("user", currentUser);
		return "admin/profile";
	}
}
