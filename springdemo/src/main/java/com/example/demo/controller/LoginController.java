package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
    private UserService userService;
	
	@GetMapping("/login")
    public String loginForm(HttpSession session) {
        if (session.getAttribute("currentUser") != null) {
            return "redirect:/admin/categories";
        }
        return "login"; 
    }


    @PostMapping("/login")
    public String login(@RequestParam String userName,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        User user = userService.login(userName, password);

        if (user != null) {
            session.setAttribute("currentUser", user);
            return "redirect:/admin/categories";
        } else {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
            return "login";
        }
    }
}
